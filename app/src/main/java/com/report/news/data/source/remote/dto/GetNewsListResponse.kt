package com.report.news.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import com.report.news.data.source.remote.entity.Articles

data class GetHeadlineListResponse(
    @SerializedName("status")
    val status       : String,

    @SerializedName("totalResults")
    val totalResults : Int,

    @SerializedName("articles")
    val articles     : List<Articles>
)