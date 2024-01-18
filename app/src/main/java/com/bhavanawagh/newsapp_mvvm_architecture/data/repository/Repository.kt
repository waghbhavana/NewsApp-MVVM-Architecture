package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Source
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkServices
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor (private val networkServices: NetworkServices){

    fun getTopHeadlines(country: String) : Flow<List<Article>>{
        return flow {
            emit(networkServices.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    fun getNewsSources():Flow<List<Source>>{
        return flow {
            emit(networkServices.getNewsSources())
        }.map {
            it.sources
        }
    }

    fun getTopHeadlinesBySource(source: String) : Flow<List<Article>>{
        return flow {
            emit(networkServices.getTopHeadlinesBySource(source))
        }.map {
            it.articles
        }
    }

    fun getTopHeadlinesByLanguage(language: String): Flow<List<Article>>{
        return flow {
            emit(networkServices.getTopHeadlinesByLanguage(language))
        }.map {
            it.articles
        }
    }
}