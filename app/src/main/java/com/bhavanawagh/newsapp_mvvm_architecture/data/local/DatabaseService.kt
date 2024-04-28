package com.bhavanawagh.newsapp_mvvm_architecture.data.local

import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface DatabaseService {

    fun getArticles(): Flow<List<Article>>

    fun deleteAllAndInsertAll(articles: List<Article>)

}