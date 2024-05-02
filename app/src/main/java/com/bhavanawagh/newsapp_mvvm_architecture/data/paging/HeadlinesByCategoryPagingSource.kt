package com.bhavanawagh.newsapp_mvvm_architecture.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.TopHeadlinesResponse
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import com.bhavanawagh.newsapp_mvvm_architecture.utils.Category


class HeadlinesByCategoryPagingSource(
    private val networkService: NetworkService,
    private val para: Pair<Category, String>,
) : PagingSource<Int, ApiArticle>() {


    override fun getRefreshKey(state: PagingState<Int, ApiArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiArticle> {
        return try {

            println("HeadlinesByCategoryPagingSource ${para.first}")
            val page = params.key ?: AppConstants.INITIAL_PAGE
            val response = loadingFun(para, page)
            LoadResult.Page(
                data = response.apiArticles,
                prevKey = if (page == AppConstants.INITIAL_PAGE) null else page.minus(1),
                nextKey = if (response.apiArticles.isEmpty()) null else page.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    private suspend fun loadingFun(para: Pair<Category, String>, page: Int): TopHeadlinesResponse {
        return when (para.first) {
            Category.SOURCE -> networkService.getTopHeadlinesBySource(
                this.para.second,
                page,
                AppConstants.PAGE_SIZE
            )

            Category.LANGUAGE -> networkService.getTopHeadlinesByLanguage(
                this.para.second,
                page,
                AppConstants.PAGE_SIZE
            )

            Category.COUNTRY -> networkService.getTopHeadlines(
                this.para.second,
                page,
                AppConstants.PAGE_SIZE
            )

            Category.SEARCH -> networkService.getTopHeadlinesBySearch(
                this.para.second,
                page,
                AppConstants.PAGE_SIZE
            )

        }

    }


}

