package com.report.news.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.report.news.data.source.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsList(newsDatas: List<NewsEntity>)

    @Query("SELECT * FROM ${NewsEntity.TABLE_NAME}")
    suspend fun getNewsList(): List<NewsEntity>

    @Query("DELETE FROM ${NewsEntity.TABLE_NAME}")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRetrieveNews(obj : NewsEntity) : Int

}