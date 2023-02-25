package com.jusqre.mobilechatgpt.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jusqre.data.datasource.ChatGPTDataSource
import com.jusqre.data.network.ChatGPTService
import com.jusqre.mobilechatgpt.util.CHAT_GPT_KEY
import com.jusqre.mobilechatgpt.util.CHAT_GPT_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIME_OUT_MILLIS = 80000L

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT_MILLIS, TimeUnit.MILLISECONDS)
        .readTimeout(TIME_OUT_MILLIS, TimeUnit.MILLISECONDS)
        .writeTimeout(TIME_OUT_MILLIS, TimeUnit.MILLISECONDS)
        .addNetworkInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $CHAT_GPT_KEY")
                .build()
            it.proceed(request)
        }
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(CHAT_GPT_URL)
            .build()

    @Singleton
    @Provides
    fun provideChatGPTService(retrofit: Retrofit): ChatGPTService =
        retrofit.create(ChatGPTService::class.java)

    @Singleton
    @Provides
    fun provideChatGPTDataSource(service: ChatGPTService): ChatGPTDataSource =
        ChatGPTDataSource(service)
}

