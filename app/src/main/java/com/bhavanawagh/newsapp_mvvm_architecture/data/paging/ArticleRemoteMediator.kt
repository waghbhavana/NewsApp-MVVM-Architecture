package com.bhavanawagh.newsapp_mvvm_architecture.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.AppDatabase
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.ArticleRemoteKeys
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.toArticleEntity
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val networkService: NetworkService,
    private val appDatabase: AppDatabase
) :
    RemoteMediator<Int, ApiArticle>() {

    private val articleDao = appDatabase.articleDao()
    private val remoteKeysDao = appDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ApiArticle>
    ): MediatorResult {
        return try {


            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentItem(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey ?: return (MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    ))
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey ?: return (MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    ))
                    nextPage
                }

            }
            val response = networkService.getTopHeadlines(AppConstants.EXTRAS_COUNTRY, 1, 10)
            val endOfPaginationReached = response.totalResults == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleDao.deleteAll()
                    remoteKeysDao.deleteAllArticleRemoteKeys()
                }


                val articleList = response.apiArticles.map {
                    it.toArticleEntity()
                }
                articleDao.insertAll(articleList)
                val keys = articleList.map { article ->
                    ArticleRemoteKeys(
                        articleKey = article.id,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                remoteKeysDao.addAllArticleRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            println(e.message)
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentItem(state: PagingState<Int, ApiArticle>): ArticleRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.toArticleEntity()?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id)
            }
        }

    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ApiArticle>): ArticleRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            remoteKeysDao.getRemoteKeys(it.toArticleEntity().id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ApiArticle>): ArticleRemoteKeys? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            remoteKeysDao.getRemoteKeys(it.toArticleEntity().id)
        }

    }
}