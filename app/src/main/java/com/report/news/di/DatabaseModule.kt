package com.report.news.di

import android.app.Application
import android.content.Context
import com.report.news.data.source.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : NewsDatabase = NewsDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideMovieDao(database: NewsDatabase) =
        database.getNewsDao()

}