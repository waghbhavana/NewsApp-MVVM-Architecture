package com.bhavanawagh.newsapp_mvvm_architecture.data.model

import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import com.google.gson.annotations.SerializedName

data class ApiArticle(

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("urlToImage")
    val imageUrl: String = "",

    @SerializedName("source")
    val sourceApi: SourceApi
)

fun ApiArticle.toArticleEntity(): Article {
    val article = Article(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = sourceApi.toSourceEntity()
    )
    println("toArticleEntity $article")
    return article;
}