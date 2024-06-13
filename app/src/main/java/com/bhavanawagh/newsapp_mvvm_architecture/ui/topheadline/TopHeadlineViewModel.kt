package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import com.bhavanawagh.newsapp_mvvm_architecture.utils.DefaultDispatcherProvider
import com.bhavanawagh.newsapp_mvvm_architecture.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider) : ViewModel() {

        private val _uiState= MutableStateFlow<UiState<List<ApiArticle>>>(UiState.Loading)
         val uiState : StateFlow<UiState<List<ApiArticle>>> = _uiState

    init {
        fetchTopHeadLines()
    }

    private fun fetchTopHeadLines() {
        viewModelScope.launch(dispatcherProvider.main) {

           topHeadlineRepository.getTopHeadlines(AppConstants.EXTRAS_COUNTRY)
               .flowOn(dispatcherProvider.io)
               .catch {
                   _uiState.value= UiState.Error(it.message.toString())
               }
               .collect{
                   _uiState.value= UiState.Success(it)
               }
        }

    }
}