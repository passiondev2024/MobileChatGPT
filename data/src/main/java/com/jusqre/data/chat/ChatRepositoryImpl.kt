package com.jusqre.data.chat

import com.jusqre.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatGPTDataSource: ChatGPTDataSource
): ChatRepository{
    override fun getChatFlowByKeyword(keyword: String): Flow<String> {
        return chatGPTDataSource.getChatGptCompletionStream(keyword)
    }
}