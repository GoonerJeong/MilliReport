package com.report.news.data.source.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.report.news.domain.model.Headline

@Entity(tableName = NewsEntity.TABLE_NAME)
class NewsEntity(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "publish_at") val publishedAt : String?,
    @PrimaryKey val url : String,
    @ColumnInfo(name = "urlToImage") val urlToImage:String?,
    @ColumnInfo(name = "is_retrieve_news") val isRetrieveNews: Boolean,
) {
    companion object {
        const val TABLE_NAME = "news"
    }

}