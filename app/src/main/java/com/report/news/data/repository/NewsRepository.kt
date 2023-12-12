package com.report.news.data.repository

import com.report.news.data.source.local.dao.NewsDao
import com.report.news.data.source.local.entity.NewsEntity
import com.report.news.ui.viewmodel.HeadlineNewsUiState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getHeadlineNews() : Flow<HeadlineNewsUiState>
    suspend fun updateRetrieveNews(obj : NewsEntity)
}