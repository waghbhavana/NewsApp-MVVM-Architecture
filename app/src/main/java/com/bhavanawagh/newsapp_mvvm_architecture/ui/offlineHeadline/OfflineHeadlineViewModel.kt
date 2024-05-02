package com.bhavanawagh.newsapp_mvvm_architecture.ui.offlineHeadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.OfflineNewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineHeadlineViewModel @Inject constructor(
    private val offlineNewsRepository: OfflineNewsRepository
) : ViewModel() {


    private val _articles = MutableStateFlow<PagingData<ApiArticle>>(value = PagingData.empty())
    val articles: StateFlow<PagingData<ApiArticle>> = _articles

    init {
        fetchTopHeadlinesByCountry(AppConstants.EXTRAS_COUNTRY)
    }

    private fun fetchTopHeadlinesByCountry(country: String) {
        viewModelScope.launch {
            offlineNewsRepository.getTopHeadlinesOfflinePaging(country)
                .collect {
                    _articles.value = it
                }
        }
    }


//    init {
//        if (cacheInterceptor.isNetworkConnected()) {
//            println("checkForInternet ${cacheInterceptor.isNetworkConnected()}")
//            fetchArticles()
//        } else {
//            fetchArticlesDirectlyFromDB()
//        }
//    }
//
//    private fun fetchArticles() {
//        viewModelScope.launch {
//            offlineNewsRepository.getArticles(AppConstants.EXTRAS_COUNTRY)
//                }.collect {
//                    println("Collect $it")
//                    _uiState.value = it
//                }
//        }
//    }
//    private fun fetchArticles(){
//        viewModelScope.launch {
//            offlineNewsRepository.getArticles(AppConstants.EXTRAS_COUNTRY)
//                .flowOn(Dispatchers.IO)
//                .catch {
//                    _uiState.value =UiState.Error(it.message.toString())
//                }
//                .collect{
//                    println("checkForInternet $it")
//                    _uiState.value= UiState.Success(it)
//                }
//        }
//    }

//    private fun fetchArticlesDirectlyFromDB() {
//        viewModelScope.launch {
//            offlineNewsRepository.getArticlesDirectFromDB()
//                .flowOn(Dispatchers.IO)
//                .catch {
//                    _uiState.value = UiState.Error(it.toString())
//                }
//                .collect {
//                    _uiState.value = UiState.Success(it)
//                }
//        }
//    }

}