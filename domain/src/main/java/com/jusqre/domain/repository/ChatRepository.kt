package com.jusqre.domain.repository

import com.jusqre.domain.model.Chat
import com.jusqre.domain.model.ChattingItem
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatFlowByKeyword(keyword: String): Flow<String>

    suspend fun getAll(): List<ChattingItem>
    suspend fun insert(id: String, list: List<Chat>)
    suspend fun deleteById(id: String)

    suspend fun update(id: String, list: List<Chat>)
}