//package com.bhavanawagh.newsapp_mvvm_architecture.ui.offlineHeadline
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.bhavanawagh.newsapp_mvvm_architecture.data.api.ForceCacheInterceptor
//import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
//import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.OfflineNewsRepository
//import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
//import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.flowOn
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class OfflineHeadlineViewModel @Inject constructor(
//    cacheInterceptor: ForceCacheInterceptor,
//    private val offlineNewsRepository: OfflineNewsRepository
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
//    val uiState: StateFlow<UiState<List<Article>>> = _uiState
//
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
////    private fun fetchArticles(){
////        viewModelScope.launch {
////            offlineNewsRepository.getArticles(AppConstants.EXTRAS_COUNTRY)
////                .flowOn(Dispatchers.IO)
////                .catch {
////                    _uiState.value =UiState.Error(it.message.toString())
////                }
////                .collect{
////                    println("checkForInternet $it")
////                    _uiState.value= UiState.Success(it)
////                }
////        }
////    }
//
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
//
//}