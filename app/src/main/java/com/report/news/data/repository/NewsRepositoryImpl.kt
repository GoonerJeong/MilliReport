package com.report.news.data.repository

import android.util.Log
import androidx.room.TypeConverter
import com.report.news.data.source.local.dao.NewsDao
import com.report.news.data.source.local.entity.NewsEntity
import com.report.news.data.source.remote.dto.GetHeadlineListResponse
import com.report.news.data.source.remote.NewsApiService
import com.report.news.data.source.remote.entity.Articles
import com.report.news.domain.model.Headline
import com.report.news.domain.translator.NewsTranslator.toNewsEntityList
import com.report.news.ui.util.Utils
import com.report.news.ui.viewmodel.HeadlineNewsUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl constructor(
    private val newsApiService : NewsApiService,
    private val newsDao: NewsDao) : NewsRepository {

    override fun getHeadlineNews(): Flow<HeadlineNewsUiState> = flow {

        try {
            emit(HeadlineNewsUiState.Loading(true))

            fetchAndInsertHeadlineNews(newsApiService, newsDao)
            emit(HeadlineNewsUiState.Success(getHeadlineNewsFromDb(newsDao)))
        }
        catch (e: HttpException) {
            errorHandling(this, e)
        } catch (e: IOException) {
            errorHandling(this, e)
        }
    }

    private suspend fun errorHandling(flow: FlowCollector<HeadlineNewsUiState>, e : Exception){
        var dbDatas = getHeadlineNewsFromDb(newsDao)
        if(dbDatas.isNotEmpty()){
            flow.emit(HeadlineNewsUiState.Success(dbDatas))
        }
        else{
            flow.emit(HeadlineNewsUiState.Error(e))
        }
    }

    override suspend fun updateRetrieveNews(obj : NewsEntity) {
        newsDao.updateRetrieveNews(obj)
    }

    private suspend fun fetchAndInsertHeadlineNews (
        newsApiService: NewsApiService,
        newsDao: NewsDao
    ) {
        var datas = newsApiService.getHeadlineNewsList("kr").articles.toNewsEntityList()
        newsDao.insertNewsList(datas)
    }

    private suspend fun getHeadlineNewsFromDb(newsDao: NewsDao): List<NewsEntity> {
        return newsDao.getNewsList()
    }

}