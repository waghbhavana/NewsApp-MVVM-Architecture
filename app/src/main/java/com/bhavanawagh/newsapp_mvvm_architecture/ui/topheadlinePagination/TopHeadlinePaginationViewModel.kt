package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadlinePagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.ForceCacheInterceptor
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlinePaginationRepository
import com.bhavanawagh.newsapp_mvvm_architecture.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinePaginationViewModel @Inject constructor(
    private val topHeadlinePaginationRepository: TopHeadlinePaginationRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val cacheInterceptor: ForceCacheInterceptor,
) :
    ViewModel() {


    private val _articles = MutableStateFlow<PagingData<ApiArticle>>(value = PagingData.empty())
    val articles: StateFlow<PagingData<ApiArticle>> = _articles

//    init {
//        if (cacheInterceptor.isNetworkConnected()) {
//            fetchTopHeadlinesByCountry(AppConstants.EXTRAS_COUNTRY)
//        } else {
//            fetchArticlesDirectlyFromDB()
//        }
//    }

    fun fetchTopHeadlinesBySource(source: String) {
        if (cacheInterceptor.isNetworkConnected()) {
            viewModelScope.launch {
                topHeadlinePaginationRepository.getTopHeadlinesBySource(source,viewModelScope)
                    .collect {
                        _articles.value = it
                    }
            }
        } else {
            fetchArticlesDirectlyFromDB()
        }
    }

    fun fetchTopHeadlinesByCountry(country: String) {
        if (cacheInterceptor.isNetworkConnected()) {
            viewModelScope.launch(dispatcherProvider.main) {
                topHeadlinePaginationRepository.getTopHeadlinesOfflinePaging(country,viewModelScope)
                    .flowOn(dispatcherProvider.io)
                    .catch {
                        println("ERRor in fetchTopHeadlinesByCountry ${it.message}")
                    }
                    .collect {
                        _articles.value = it
                    }
            }
        } else {
            fetchArticlesDirectlyFromDB()
        }
    }

    fun fetchTopHeadlinesByLanguage(language: String) {
        if (cacheInterceptor.isNetworkConnected()) {
            viewModelScope.launch {
                topHeadlinePaginationRepository.getTopHeadlinesByLanguage(language,viewModelScope)
                    .collect {
                        _articles.value = it
                    }
            }
        } else {
            fetchArticlesDirectlyFromDB()
        }
    }

    private fun fetchArticlesDirectlyFromDB() {
        println("fetchArticlesDirectlyFromDB")
        viewModelScope.launch {
            topHeadlinePaginationRepository.getArticlesDirectFromDB().collect {
                _articles.value = it
            }
        }
    }

}