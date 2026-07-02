package com.snaplist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ListingDraft::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SnapListDb : RoomDatabase() {
    abstract fun draftDao(): DraftDao

    companion object {
        fun build(context: Context): SnapListDb =
            Room.databaseBuilder(context, SnapListDb::class.java, "snaplist.db").build()
    }
}
