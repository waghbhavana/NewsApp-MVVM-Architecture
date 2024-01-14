package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinesRepository @Inject constructor (private val networkServices: NetworkServices){

    fun getTopHeadlines(country: String) : Flow<List<Article>>{
        return flow {
            emit(networkServices.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }
}