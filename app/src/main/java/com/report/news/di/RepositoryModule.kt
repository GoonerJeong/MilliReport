package com.report.news.di

import com.report.news.data.repository.NewsRepository
import com.report.news.data.repository.NewsRepositoryImpl
import com.report.news.data.source.local.dao.NewsDao
import com.report.news.data.source.remote.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepositoryImpl(
        newsApiService: NewsApiService,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApiService, newsDao)

}