package com.report.news.domain.usecase

import android.util.Log
import com.report.news.data.repository.NewsRepository
import com.report.news.data.source.local.entity.NewsEntity
import com.report.news.domain.model.Headline
import com.report.news.domain.translator.NewsTranslator.toHeadlineModelList
import com.report.news.ui.viewmodel.HeadlineNewsUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class GetHeadlineNewsListUseCase  constructor(
    private val userRepository: NewsRepository
) {

    operator fun invoke(): Flow<HeadlineNewsUiState>{
        return userRepository.getHeadlineNews()
    }
}