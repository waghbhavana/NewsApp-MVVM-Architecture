package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.DatabaseService
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class OfflineNewsRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {


    fun getArticles(country: String): Flow<List<Article>> {
        return flow { emit(networkService.getTopHeadlines(country)) }
            .map {
                it.apiArticles.map { apiArticle: ApiArticle ->
                    apiArticle.toArticleEntity()
                }
            }.flatMapConcat { articles ->
                flow { emit(databaseService.deleteAllAndInsertAll((articles))) }
            }.flatMapConcat {
                databaseService.getArticles()
            }
    }

    fun getArticlesDirectFromDB(): Flow<List<Article>> {
        return databaseService.getArticles()
    }
}