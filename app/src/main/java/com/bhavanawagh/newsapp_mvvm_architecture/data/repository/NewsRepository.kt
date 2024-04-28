package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import android.util.Log
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Country
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.apiArticles
        }
    }

    fun getNewsSources(): Flow<List<SourceApi>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }

    fun getTopHeadlinesBySource(source: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getTopHeadlinesBySource(source))
        }.map {
            it.apiArticles
        }
    }

    fun getTopHeadlinesByLanguage(language: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getTopHeadlinesByLanguage(language))
        }.map {
            it.apiArticles
        }
    }

    fun getTopHeadlinesBySearch(country: String, query: String): Flow<List<ApiArticle>> {
        Log.d("NewsRepository", "API CALL - query-${query}")
        return flow {
            emit(networkService.getTopHeadlinesBySearch(country, query))
        }.map {
            it.apiArticles
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