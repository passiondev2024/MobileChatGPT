package com.jusqre.data.datasource

import com.jusqre.data.database.dao.ChattingItemDao
import com.jusqre.data.database.entity.ChattingItemEntity
import com.jusqre.domain.model.Chat
import com.jusqre.domain.model.ChattingItem
import javax.inject.Inject

class ChatLocalDatasource @Inject constructor(
    private val chattingItemDao: ChattingItemDao
) {
    suspend fun getAllItem(): List<ChattingItem> =
        chattingItemDao.getAll().map { ChattingItem(it.chatId, it.chatList, it.lastChat) }

    suspend fun insert(id: String, list: List<Chat>, last: String) = chattingItemDao.insert(
        ChattingItemEntity(id, list, last)
    )

    suspend fun deleteById(id: String) =
        chattingItemDao.deleteById(id)

    suspend fun update(id:String, list: List<Chat>, last: String) = chattingItemDao.update(
        ChattingItemEntity(id, list, last)
    )
}