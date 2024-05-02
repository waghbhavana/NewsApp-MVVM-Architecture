package com.bhavanawagh.newsapp_mvvm_architecture.data.local

import androidx.paging.PagingSource
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import javax.inject.Singleton

@Singleton
interface DatabaseService {

    fun getArticles(): PagingSource<Int, Article>

    fun deleteAllAndInsertAll(articles: List<Article>)

}