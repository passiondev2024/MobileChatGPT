package com.jusqre.domain.repository

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatFlowByKeyword(keyword: String): Flow<String>
}