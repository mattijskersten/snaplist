package com.snaplist.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DraftDao {
    @Query("SELECT * FROM drafts ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<ListingDraft>>

    @Query("SELECT * FROM drafts WHERE id = :id")
    fun observe(id: Long): Flow<ListingDraft?>

    @Query("SELECT * FROM drafts WHERE id = :id")
    suspend fun get(id: Long): ListingDraft?

    @Insert
    suspend fun insert(draft: ListingDraft): Long

    @Update
    suspend fun update(draft: ListingDraft)

    @Delete
    suspend fun delete(draft: ListingDraft)
}
