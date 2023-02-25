package com.jusqre.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jusqre.data.database.converter.ChatListConverter
import com.jusqre.data.database.dao.ChattingItemDao
import com.jusqre.data.database.entity.ChattingItemEntity

@Database(
    entities = [ChattingItemEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ChatListConverter::class)
abstract class MobileChatGPTDatabase : RoomDatabase() {
    abstract fun chattingItemDao(): ChattingItemDao
}