package com.jusqre.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChattingItem(
    val chatId: String,
    val chatList: List<Chat>,
    val lastChat: String
) : Parcelable
