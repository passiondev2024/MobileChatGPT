package com.jusqre.data.repository

import com.jusqre.data.datasource.ChatGPTDataSource
import com.jusqre.data.datasource.ChatLocalDatasource
import com.jusqre.domain.model.Chat
import com.jusqre.domain.model.ChattingItem
import com.jusqre.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatGPTDataSource: ChatGPTDataSource,
    private val chatLocalDatasource: ChatLocalDatasource
) : ChatRepository {
    override fun getChatFlowByKeyword(keyword: String): Flow<String> {
        return chatGPTDataSource.getChatGptCompletionStream(keyword)
    }

    override suspend fun getAll(): List<ChattingItem> {
        return chatLocalDatasource.getAllItem()
    }

    override suspend fun insert(id: String, list: List<Chat>) {
        if (list.isNotEmpty()) {
            return chatLocalDatasource.insert(id, list, list.last().text)
        }
        return chatLocalDatasource.insert(id, list, "asdf")
    }

    override suspend fun deleteById(id: String) {
        return chatLocalDatasource.deleteById(id)
    }

    override suspend fun update(id: String, list: List<Chat>) {
        return chatLocalDatasource.update(id, list, list.last().text)
    }
}