package com.bhavanawagh.newsapp_mvvm_architecture.data.local

import androidx.paging.PagingSource
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article

class AppDatabaseService constructor(private val appDatabase: AppDatabase) : DatabaseService {

    override fun getArticles(): PagingSource<Int, Article> {
        return appDatabase.articleDao().getAll()

    }

    override fun deleteAllAndInsertAll(articles: List<Article>) {
        appDatabase.articleDao().deleteAndInsertAll(articles)
    }

}