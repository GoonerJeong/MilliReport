package com.report.news.data.source.local.convertor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.report.news.data.source.local.entity.NewsEntity
import com.report.news.data.source.remote.entity.Articles
import com.report.news.data.source.remote.entity.Source
import com.report.news.domain.model.Headline
import com.report.news.ui.util.Utils
import java.io.ByteArrayOutputStream

class NewsTypeConverter {

    // Bitmap -> ByteArray 변환
    @TypeConverter
    fun toByteArray(bitmap : Bitmap) : ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    // ByteArray -> Bitmap 변환
    @TypeConverter
    fun toBitmap(bytes : ByteArray) : Bitmap{
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun  toSource(name:String):Source{
        return Source(name,name)
    }

}