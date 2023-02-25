package com.jusqre.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jusqre.data.database.entity.ChattingItemEntity

@Dao
interface ChattingItemDao {
    @Query("SELECT * FROM ChattingItemEntity")
    suspend fun getAll(): List<ChattingItemEntity>

    @Insert
    suspend fun insert(item: ChattingItemEntity)

    @Query("DELETE FROM ChattingItemEntity WHERE chatting_id = :chatId")
    suspend fun deleteById(chatId: String)

    @Update
    suspend fun update(item: ChattingItemEntity)
}