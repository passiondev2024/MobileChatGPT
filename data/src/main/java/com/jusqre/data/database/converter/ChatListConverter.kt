package com.jusqre.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jusqre.domain.model.Chat

class ChatListConverter {
    @TypeConverter
    fun fromChatList(chatList: List<Chat>): String {
        val gson = Gson()
        return gson.toJson(chatList)
    }

    @TypeConverter
    fun toChatList(chatListString: String): List<Chat> {
        val gson = Gson()
        val listType = object : TypeToken<List<Chat>>() {}.type
        return gson.fromJson(chatListString, listType)
    }
}