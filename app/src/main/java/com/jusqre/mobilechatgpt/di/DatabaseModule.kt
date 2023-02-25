package com.jusqre.mobilechatgpt.di

import android.content.Context
import androidx.room.Room
import com.jusqre.data.database.MobileChatGPTDatabase
import com.jusqre.data.database.dao.ChattingItemDao
import com.jusqre.data.datasource.ChatLocalDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MobileChatGPTDatabase::class.java,
            "boost_search_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideChattingItemDao(database: MobileChatGPTDatabase) = database.chattingItemDao()

    @Singleton
    @Provides
    fun provideChatLocalDataSource(dao: ChattingItemDao): ChatLocalDatasource =
        ChatLocalDatasource(dao)
}
