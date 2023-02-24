package com.jusqre.data.network

import com.jusqre.data.model.ChatGPTRequestDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface ChatGPTService {
    @POST("completions")
    @Streaming
    suspend fun getChatGptCompletionStream(@Body request: ChatGPTRequestDTO): Response<ResponseBody>
}
