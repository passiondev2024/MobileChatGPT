package com.jusqre.data.chat

import com.google.gson.Gson
import com.jusqre.data.model.ChatGPTRequestDTO
import com.jusqre.data.model.ChatGPTResponseDTO
import com.jusqre.data.network.ChatGPTService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChatGPTDataSource @Inject constructor(
    private val service: ChatGPTService
) {
    fun getChatGptCompletionStream(text: String): Flow<String> = flow {
        val response = service.getChatGptCompletionStream(ChatGPTRequestDTO(text))
        response.body()?.byteStream()?.bufferedReader()?.useLines { lines ->
            lines.filter { it.startsWith("data:") && !it.contains("DONE") }
                .map { it.removePrefix("data:").trim() }
                .forEach { dataJson ->
                    val responseDTO = Gson().fromJson(dataJson, ChatGPTResponseDTO::class.java)
                    emit(responseDTO.choices[0].text)
                }
        }
    }.flowOn(Dispatchers.IO)
}
