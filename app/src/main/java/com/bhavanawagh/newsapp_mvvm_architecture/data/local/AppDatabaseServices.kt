package com.bhavanawagh.newsapp_mvvm_architecture.data.local

import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import kotlinx.coroutines.flow.Flow

class AppDatabaseServices constructor(private val appDatabase: AppDatabase) : DatabaseService {

    override fun getArticles(): Flow<List<Article>> {
        return appDatabase.articleDao().getAll()
    }

    override fun deleteAllAndInsertAll(articles: List<Article>) {
        appDatabase.articleDao().deleteAndInsertAll(articles)
    }
}