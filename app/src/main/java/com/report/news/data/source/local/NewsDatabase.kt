package com.report.news.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.report.news.data.source.local.convertor.NewsTypeConverter
import com.report.news.data.source.local.dao.NewsDao
import com.report.news.data.source.local.entity.NewsEntity


@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun getNewsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database.db"
                ).
                build()
                INSTANCE = instance
                return instance
            }
        }
    }
}