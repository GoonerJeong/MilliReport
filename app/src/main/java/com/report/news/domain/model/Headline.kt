package com.report.news.domain.model

import android.graphics.Bitmap

data class Headline(
   var title       : String? = null,
   var url         : String? = null,
   var urlToImage : String ?= null,
   var publishedAt : String? = null,
   var isRetrieveNews : Boolean =false
)