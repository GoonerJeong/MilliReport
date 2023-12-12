package com.report.news.domain.usecase

import android.util.Log
import com.report.news.data.repository.NewsRepository
import com.report.news.data.source.local.entity.NewsEntity
import com.report.news.domain.model.Headline
import com.report.news.domain.translator.NewsTranslator.toHeadlineModelList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class UpdateRetrieveNewsUseCase  constructor(
    private val userRepository: NewsRepository,
) {

    suspend operator fun invoke(obj : NewsEntity) {
        return userRepository.updateRetrieveNews(obj)
    }
}