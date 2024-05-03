package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

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
import com.bhavanawagh.newsapp_mvvm_architecture.data.paging.ArticleRemoteMediator
import com.bhavanawagh.newsapp_mvvm_architecture.data.paging.HeadlinesByCategoryPagingSource
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import com.bhavanawagh.newsapp_mvvm_architecture.utils.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineNewsRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getTopHeadlinesOfflinePaging(country: String): Flow<PagingData<ApiArticle>> {
        val countryD = Pair(Category.COUNTRY, country)
        return Pager(
            config = PagingConfig(pageSize = AppConstants.PAGE_SIZE, prefetchDistance = 20),
            remoteMediator = ArticleRemoteMediator(networkService, appDatabase),
            pagingSourceFactory = { HeadlinesByCategoryPagingSource(networkService, countryD) }
        ).flow
    }

//    fun getArticles(country: String){
    // : Flow<List<Article>>
//        return flow { emit(networkService.getTopHeadlines(country)) }
//            .map {
//                it.apiArticles.map { apiArticle: ApiArticle ->
//                    apiArticle.toArticleEntity()
//                }
//            }.flatMapConcat { articles ->
//                flow { emit(databaseService.deleteAllAndInsertAll((articles))) }
//            }.flatMapConcat {
//                databaseService.getArticles()
//            }

//        return
//    }

    fun getArticlesDirectFromDB(): Flow<PagingData<ApiArticle>> = Pager(
        PagingConfig(pageSize = AppConstants.PAGE_SIZE, prefetchDistance = 20)
    ) {
        databaseService.getArticles()
    }.flow
        .map { value: PagingData<Article> ->
            value.map { article -> article.toArticleApi() }
        }

}
