package com.report.news.data.source.remote

import com.report.news.data.source.remote.dto.GetHeadlineListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getHeadlineNewsList(@Query("country") country : String): GetHeadlineListResponse

}