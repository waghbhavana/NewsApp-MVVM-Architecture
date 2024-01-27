package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import android.util.Log
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Source
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Country
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    fun getNewsSources(): Flow<List<Source>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }

    fun getTopHeadlinesBySource(source: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlinesBySource(source))
        }.map {
            it.articles
        }
    }

    fun getTopHeadlinesByLanguage(language: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlinesByLanguage(language))
        }.map {
            it.articles
        }
    }

    fun getTopHeadlinesBySearch(country: String, query: String): Flow<List<Article>> {
        Log.d("NewsRepository", "API CALL - query-${query}")
        return flow {
            emit(networkService.getTopHeadlinesBySearch(country, query))
        }.map {
            it.articles
        }
    }

    fun getCountryList(): Flow<List<Country>> {
        return flow {
            emit(AppConstants.COUNTRY_LIST)
        }.map {
            it
        }
    }

    fun getLanguageList(): Flow<List<Language>> {
        return flow {
            emit(AppConstants.LANGUAGE_LIST)
        }.map {
            it
        }
    }
}