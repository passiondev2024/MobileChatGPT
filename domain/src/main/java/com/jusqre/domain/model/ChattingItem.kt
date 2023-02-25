package com.jusqre.domain.model

data class ChattingItem(
    val chatId: String,
    val chatList: List<Chat>,
    val lastChat: String
)
