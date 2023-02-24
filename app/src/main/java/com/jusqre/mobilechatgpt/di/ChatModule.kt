package com.jusqre.mobilechatgpt.di

import com.jusqre.data.chat.ChatRepositoryImpl
import com.jusqre.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ChatModule {
    @Singleton
    @Provides
    fun provideRepository(impl: ChatRepositoryImpl): ChatRepository = impl
}
