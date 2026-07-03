package com.snaplist.overlay

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.snaplist.MainActivity
import com.snaplist.appContainer
import com.snaplist.ui.handoff.handoffFields
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Floating copy helper shown over the Vinted app during handoff: a draggable
 * chip that expands into the list of listing fields, each tap-to-copy. The
 * foreground notification carries a "copy next field" action as a
 * no-overlay-permission fallback and for one-handed use.
 */
class HandoffOverlayService : Service(), LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateController = SavedStateRegistryController.create(this)
    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val savedStateRegistry: SavedStateRegistry get() = savedStateController.savedStateRegistry
    override val viewModelStore = ViewModelStore()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private lateinit var windowManager: WindowManager
    private var overlayView: ComposeView? = null
    private lateinit var layoutParams: WindowManager.LayoutParams

    private val fields = mutableStateListOf<Pair<String, String>>()
    private val copied = mutableStateListOf<String>()
    private val expanded = mutableStateOf(false)
    private val nextIndex = mutableIntStateOf(0)

    override fun onCreate() {
        super.onCreate()
        savedStateController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_COPY_NEXT -> copyNext()
            ACTION_STOP -> stopSelf()
            else -> {
                val draftId = intent?.getLongExtra(EXTRA_DRAFT_ID, -1L) ?: -1L
                if (draftId == -1L) {
                    stopSelf()
                } else {
                    startForeground(NOTIFICATION_ID, buildNotification())
                    loadDraft(draftId)
                }
            }
        }
        return START_NOT_STICKY
    }

    private fun loadDraft(draftId: Long) {
        scope.launch {
            val draft = appContainer().draftDao.get(draftId)
            if (draft == null) {
                stopSelf()
                return@launch
            }
            fields.clear()
            fields.addAll(draft.handoffFields())
            nextIndex.intValue = 0
            updateNotification()
            if (overlayView == null && android.provider.Settings.canDrawOverlays(this@HandoffOverlayService)) {
                showOverlay()
            }
        }
    }

    private fun showOverlay() {
        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT,
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 24
            y = 240
        }

        val view = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@HandoffOverlayService)
            setViewTreeSavedStateRegistryOwner(this@HandoffOverlayService)
            setViewTreeViewModelStoreOwner(this@HandoffOverlayService)
            setContent {
                OverlayPanel(
                    fields = fields,
                    copiedLabels = copied,
                    expanded = expanded.value,
                    onToggle = { expanded.value = !expanded.value },
                    onCopy = { label, value -> copyField(label, value) },
                    onDrag = { dx, dy -> moveBy(dx, dy) },
                    onClose = { stopSelf() },
                )
            }
        }
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
        windowManager.addView(view, layoutParams)
        overlayView = view
    }

    private fun moveBy(dx: Float, dy: Float) {
        layoutParams.x += dx.roundToInt()
        layoutParams.y += dy.roundToInt()
        overlayView?.let { windowManager.updateViewLayout(it, layoutParams) }
    }

    private fun copyField(label: String, value: String) {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, value))
        if (label !in copied) copied.add(label)
        // Advance "copy next" past everything already copied.
        nextIndex.intValue = fields.indexOfFirst { it.first !in copied }.let {
            if (it == -1) fields.size else it
        }
        updateNotification()
    }

    private fun copyNext() {
        val field = fields.getOrNull(nextIndex.intValue) ?: return
        copyField(field.first, field.second)
    }

    private fun updateNotification() {
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NOTIFICATION_ID, buildNotification())
    }

    private fun buildNotification(): Notification {
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                "Vinted handoff helper",
                NotificationManager.IMPORTANCE_LOW,
            )
        )

        val next = fields.getOrNull(nextIndex.intValue)
        val text = when {
            fields.isEmpty() -> "Loading listing fields…"
            next == null -> "All ${fields.size} fields copied ✓"
            else -> "${copied.size}/${fields.size} copied — next: ${next.first}"
        }

        val openApp = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE,
        )
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_edit)
            .setContentTitle("SnapList — paste into Vinted")
            .setContentText(text)
            .setContentIntent(openApp)
            .setOngoing(true)
            .setSilent(true)
        if (next != null) {
            builder.addAction(
                0, "Copy ${next.first}",
                servicePendingIntent(1, ACTION_COPY_NEXT),
            )
        }
        builder.addAction(0, "Done", servicePendingIntent(2, ACTION_STOP))
        return builder.build()
    }

    private fun servicePendingIntent(requestCode: Int, action: String): PendingIntent =
        PendingIntent.getService(
            this, requestCode,
            Intent(this, HandoffOverlayService::class.java).setAction(action),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

    override fun onDestroy() {
        overlayView?.let { windowManager.removeView(it) }
        overlayView = null
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        viewModelStore.clear()
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        private const val CHANNEL_ID = "handoff_helper"
        private const val NOTIFICATION_ID = 42
        private const val EXTRA_DRAFT_ID = "draft_id"
        private const val ACTION_COPY_NEXT = "com.snaplist.overlay.COPY_NEXT"
        private const val ACTION_STOP = "com.snaplist.overlay.STOP"

        fun start(context: Context, draftId: Long) {
            context.startForegroundService(
                Intent(context, HandoffOverlayService::class.java)
                    .putExtra(EXTRA_DRAFT_ID, draftId)
            )
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, HandoffOverlayService::class.java))
        }
    }
}
