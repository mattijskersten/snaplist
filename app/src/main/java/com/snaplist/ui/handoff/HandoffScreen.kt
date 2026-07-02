package com.snaplist.ui.handoff

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.snaplist.appContainer
import com.snaplist.data.db.DraftStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.rememberCoroutineScope
import java.io.File

private val VINTED_PACKAGES = listOf("com.vinted.android", "com.vinted")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandoffScreen(
    draftId: Long,
    onDone: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val container = context.appContainer()
    val scope = rememberCoroutineScope()
    val draft by container.draftDao.observe(draftId).collectAsState(initial = null)
    val settings by container.settings.settings.collectAsState()
    val copied = remember { mutableStateMapOf<String, Boolean>() }
    var savedToGallery by remember { mutableStateOf<Int?>(null) }

    val current = draft ?: return

    fun copy(label: String, value: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, value))
        copied[label] = true
    }

    fun vintedPackage(): String? =
        VINTED_PACKAGES.firstOrNull { pkg ->
            runCatching { context.packageManager.getPackageInfo(pkg, 0) }.isSuccess
        }

    fun sharePhotos() {
        val uris = ArrayList<Uri>(
            current.photoPaths.map { container.photos.shareUri(File(it)) }
        )
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "image/jpeg"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            vintedPackage()?.let { setPackage(it) }
        }
        val toLaunch = if (intent.`package` != null &&
            intent.resolveActivity(context.packageManager) != null
        ) {
            intent
        } else {
            Intent.createChooser(intent.apply { setPackage(null) }, "Share photos")
        }
        context.startActivity(toLaunch)
    }

    fun openVinted() {
        val pkg = vintedPackage()
        val intent = pkg?.let { context.packageManager.getLaunchIntentForPackage(it) }
            ?: Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vinted.com/items/new"))
        context.startActivity(intent)
    }

    val fields = buildList {
        add("Title" to current.title)
        add("Description" to current.description)
        if (current.brand.isNotBlank()) add("Brand" to current.brand)
        if (current.categoryPath.isNotEmpty()) add("Category" to current.categoryPath.joinToString(" › "))
        current.condition?.let { add("Condition" to it.label) }
        if (current.size.isNotBlank()) add("Size" to current.size)
        if (current.colors.isNotEmpty()) add("Colors" to current.colors.joinToString(", "))
        if (current.material.isNotBlank()) add("Material" to current.material)
        current.price?.let { add("Price" to it.toString()) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post to Vinted") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "1. Send the photos to Vinted — this opens a new listing there with the " +
                    "photos attached.\n" +
                    "2. Copy each field below and paste it into Vinted's form " +
                    "(split-screen makes this quick).",
                style = MaterialTheme.typography.bodyMedium,
            )

            Button(onClick = { sharePhotos() }, modifier = Modifier.fillMaxWidth()) {
                Text("Send ${current.photoPaths.size} photos to Vinted")
            }
            OutlinedButton(
                onClick = {
                    scope.launch {
                        val n = withContext(Dispatchers.IO) {
                            container.photos.exportToGallery(current.photoPaths)
                        }
                        savedToGallery = n
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    savedToGallery?.let { "Saved $it photos to gallery ✓" }
                        ?: "Save photos to gallery instead"
                )
            }
            OutlinedButton(onClick = { openVinted() }, modifier = Modifier.fillMaxWidth()) {
                Text("Open Vinted")
            }
            Text(
                "If sharing doesn't open Vinted's sell flow, save the photos to the gallery, " +
                    "open Vinted, start a listing and pick them from Pictures/SnapList.",
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(Modifier.height(8.dp))
            Text("Tap to copy:", style = MaterialTheme.typography.labelLarge)

            fields.forEach { (label, value) ->
                ListItem(
                    headlineContent = { Text(label) },
                    supportingContent = {
                        Text(value, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    },
                    trailingContent = {
                        IconButton(onClick = { copy(label, value) }) {
                            if (copied[label] == true) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Copied",
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            } else {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy $label")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    scope.launch {
                        container.draftDao.get(draftId)?.let {
                            container.draftDao.update(it.copy(status = DraftStatus.POSTED))
                        }
                        onDone()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Mark as posted")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
