package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.AppDatabase
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.DatabaseService
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.toArticleApi
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Country
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi
import com.bhavanawagh.newsapp_mvvm_architecture.data.paging.ArticleRemoteMediator
import com.bhavanawagh.newsapp_mvvm_architecture.data.paging.HeadlinesByCategoryPagingSource
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.PAGE_SIZE
import com.bhavanawagh.newsapp_mvvm_architecture.utils.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinePaginationRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {


//    @OptIn(ExperimentalPagingApi::class)
//    fun getTopHeadlines(country: String): Flow<PagingData<ApiArticle>> {
//        val countryD = Pair(Category.COUNTRY, country)
//        return Pager(
//            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 20),
//            remoteMediator = ArticleRemoteMediator(networkService, appDatabase),
//            pagingSourceFactory = { HeadlinesByCategoryPagingSource(networkService, countryD) }
//        ).flow
//    }

    @OptIn(ExperimentalPagingApi::class)
    fun getTopHeadlinesOfflinePaging(country: String): Flow<PagingData<ApiArticle>> {
        val countryD = Pair(Category.COUNTRY, country)
        return Pager(
            config = PagingConfig(pageSize = AppConstants.PAGE_SIZE, prefetchDistance = 20),
            remoteMediator = ArticleRemoteMediator(networkService, appDatabase),
            pagingSourceFactory = { HeadlinesByCategoryPagingSource(networkService, countryD) }
        ).flow
    }


    fun getNewsSources(): Flow<List<SourceApi>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getTopHeadlinesBySource(source: String): Flow<PagingData<ApiArticle>> {

        val sourceD = Pair(Category.SOURCE, source)
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = ArticleRemoteMediator(networkService, appDatabase),
            pagingSourceFactory = { HeadlinesByCategoryPagingSource(networkService, sourceD) }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getTopHeadlinesByLanguage(language: String): Flow<PagingData<ApiArticle>> {
        val languageD = Pair(Category.LANGUAGE, language)
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = ArticleRemoteMediator(networkService, appDatabase),
            pagingSourceFactory = { HeadlinesByCategoryPagingSource(networkService, languageD) }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getTopHeadlinesBySearch(query: String): Flow<PagingData<ApiArticle>> {
        Log.d("NewsRepository", "API CALL - query-${query}")
        val searchQ = Pair(Category.SEARCH, query)
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = ArticleRemoteMediator(networkService, appDatabase),
            pagingSourceFactory = { HeadlinesByCategoryPagingSource(networkService, searchQ) }
        ).flow
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

    fun getArticlesDirectFromDB(): Flow<PagingData<ApiArticle>> = Pager(

        PagingConfig(pageSize = AppConstants.PAGE_SIZE, prefetchDistance = 20)
    ) {
        databaseService.getArticles()
    }.flow
        .map { value: PagingData<Article> ->
            value.map { article -> article.toArticleApi() }
        }


}