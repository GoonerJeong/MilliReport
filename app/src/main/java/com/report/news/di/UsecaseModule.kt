package com.report.news.di

import com.report.news.data.repository.NewsRepository
import com.report.news.domain.usecase.GetHeadlineNewsListUseCase
import com.report.news.domain.usecase.UpdateRetrieveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsecaseModule {

    @Provides
    @Singleton
    fun provideGetHeadlineNewsListUseCase(repository: NewsRepository): GetHeadlineNewsListUseCase =
        GetHeadlineNewsListUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateRetrieveNewsUseCase(repository: NewsRepository): UpdateRetrieveNewsUseCase =
        UpdateRetrieveNewsUseCase(repository)

}