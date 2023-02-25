package com.jusqre.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    var text: String,
    val isMyText: Boolean
): Parcelable
