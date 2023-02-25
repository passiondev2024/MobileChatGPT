package com.jusqre.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jusqre.data.database.converter.ChatListConverter
import com.jusqre.domain.model.Chat

@Entity
data class ChattingItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "chatting_id")
    val chatId: String,
    @TypeConverters(ChatListConverter::class)
    @ColumnInfo(name = "chatting_list")
    val chatList: List<Chat>,
    @ColumnInfo(name = "chatting_last")
    val lastChat: String
)
