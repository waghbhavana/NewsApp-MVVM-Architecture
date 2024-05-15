package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadlinePagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlinePaginationRepository
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import com.bhavanawagh.newsapp_mvvm_architecture.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinePaginationViewModel @Inject constructor(private val topHeadlinePaginationRepository: TopHeadlinePaginationRepository,
                                                         private val dispatcherProvider: DispatcherProvider) :
    ViewModel() {


    private val _uiState = MutableStateFlow<PagingData<ApiArticle>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<ApiArticle>> = _uiState

    init {
        fetchTopHeadlinesByCountry(AppConstants.EXTRAS_COUNTRY)
    }


    fun fetchTopHeadlinesBySource(source: String) {
        viewModelScope.launch {
            topHeadlinePaginationRepository.getTopHeadlinesBySource(source)
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun fetchTopHeadlinesByCountry(country: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlinePaginationRepository.getTopHeadlines(country)
                .flowOn(dispatcherProvider.io)
                .catch {
                   println("ERRor in fetchTopHeadlinesByCountry ${it.message}")
                }
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun fetchTopHeadlinesByLanguage(language: String) {
        viewModelScope.launch {
            topHeadlinePaginationRepository.getTopHeadlinesByLanguage(language)
                .collect {
                    _uiState.value = it
                }
        }
    }

}