package com.bhavanawagh.newsapp_mvvm_architecture.data.model

import com.google.gson.annotations.SerializedName

data class TopHeadlinesResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val apiArticles: List<ApiArticle> = ArrayList(),
)

