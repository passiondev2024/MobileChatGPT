package com.jusqre.data.model

data class ChatGPTResponseDTO(
    val choices: List<ChatGPTChoiceDTO>
)

data class ChatGPTChoiceDTO(
    val text: String,
    val index: Int,
    val logprobs: Any?,
    val finish_reason: Any?
)