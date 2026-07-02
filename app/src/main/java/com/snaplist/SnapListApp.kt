package com.snaplist

import android.app.Application
import android.content.Context
import com.snaplist.data.ai.ListingAnalyzer
import com.snaplist.data.db.SnapListDb
import com.snaplist.data.photos.PhotoStore
import com.snaplist.data.settings.SettingsStore

class AppContainer(context: Context) {
    val db by lazy { SnapListDb.build(context) }
    val draftDao by lazy { db.draftDao() }
    val settings by lazy { SettingsStore(context) }
    val photos by lazy { PhotoStore(context) }
    val analyzer by lazy { ListingAnalyzer() }
}

class SnapListApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

fun Context.appContainer(): AppContainer =
    (applicationContext as SnapListApp).container
