package com.jusqre.data.model

data class ChatGPTRequestDTO(
    val prompt: String,
    val model: String = "text-davinci-003",
    val max_tokens: Int = 2048,
    val n: Int = 1,
    val stream: Boolean = true
)
