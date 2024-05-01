package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.NewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {


    private val _uiState = MutableStateFlow<PagingData<ApiArticle>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<ApiArticle>> = _uiState

    init {
        fetchTopHeadlinesByCountry(AppConstants.EXTRAS_COUNTRY)
    }


    fun fetchTopHeadlinesBySource(source: String) {
        viewModelScope.launch {
            newsRepository.getTopHeadlinesBySource(source)
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun fetchTopHeadlinesByCountry(country: String) {
        viewModelScope.launch {
            newsRepository.getTopHeadlines(country)
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun fetchTopHeadlinesByLanguage(language: String) {
        viewModelScope.launch {
            newsRepository.getTopHeadlinesByLanguage(language)
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun fetchTopHeadlinesBySearch(query: String) {

        viewModelScope.launch {
            newsRepository.getTopHeadlinesBySearch(query)
                .collect {
                    _uiState.value =it
                }
        }
    }

//    fun fetchTopHeadlinesByLanguage(language: String) {
//        viewModelScope.launch {
//            newsRepository.getTopHeadlinesByLanguage(language)
//                .catch {
//                    _uiState.value = UiState.Error(it.toString())
//                }
//                .collect {
//                    _uiState.value = UiState.Success(it)
//                }
//        }
//    }

//    fun fetchTopHeadlinesBySearch(country: String, query: String) {
//
//        viewModelScope.launch {
//            newsRepository.getTopHeadlinesBySearch(country, query)
//                .catch {
//                    _uiState.value = UiState.Error(it.toString())
//                }
//                .collect {
//                    _uiState.value = UiState.Success(it)
//                }
//        }
//    }
}