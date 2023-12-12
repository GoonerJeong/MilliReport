package com.report.news.domain.translator

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.report.news.data.source.local.entity.NewsEntity
import com.report.news.data.source.remote.entity.Articles
import com.report.news.domain.model.Headline
import com.report.news.ui.util.Utils

object NewsTranslator {
    fun List<NewsEntity>.toHeadlineModelList() = map {
        Headline(it.title, it.url,  it.urlToImage, it.publishedAt, it.isRetrieveNews)
    }

    fun Headline.toNewsEntity(): NewsEntity {
        return NewsEntity(
            title = title,
            url = url!!,
            publishedAt = publishedAt,
            urlToImage = urlToImage,
            isRetrieveNews = isRetrieveNews
        )
    }

    fun List<Articles>.toNewsEntityList() = map {
        NewsEntity( it.title, it.publishedAt, it.url, it.urlToImage,  false)
    }
}