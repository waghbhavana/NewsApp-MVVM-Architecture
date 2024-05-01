package com.bhavanawagh.newsapp_mvvm_architecture.data.repository


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.EXTRAS_COUNTRY
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.INITIAL_PAGE
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.PAGE_SIZE

class TopHeadlinePagingSource(private val networkService: NetworkService) :
    PagingSource<Int, ApiArticle>() {

    override fun getRefreshKey(state: PagingState<Int, ApiArticle>): Int? {
      return  state.anchorPosition?.let { anchorPosition ->
                 state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
              ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)}
    }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiArticle> {
        return try {
            val page = params.key ?: INITIAL_PAGE

            val response = networkService.getTopHeadlines(
                EXTRAS_COUNTRY,
                page,
                PAGE_SIZE
            )

            LoadResult.Page(
                data = response.apiArticles,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
                nextKey = if (response.apiArticles.isEmpty()) null else page.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


//    suspend fun loadArticlesBySource( params: LoadParams<Int>, source:String): LoadResult<Int, ApiArticle> {
//        return try {
//           val params: LoadParams<Int>
//            val page = params.key ?: INITIAL_PAGE
//            val response = networkService.getTopHeadlinesBySource(
//                source,
//                INITIAL_PAGE,
//                PAGE_SIZE
//            )
//
//            LoadResult.Page(
//                data = response.apiArticles,
//                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
//                nextKey = if (response.apiArticles.isEmpty()) null else page.plus(1)
//            )
//
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
}