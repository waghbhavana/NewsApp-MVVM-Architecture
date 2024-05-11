package com.bhavanawagh.newsapp_mvvm_architecture.ui.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlinePaginationRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(private val topHeadlinePaginationRepository: TopHeadlinePaginationRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<SourceApi>>>(UiState.Loading)
    val uiState: MutableStateFlow<UiState<List<SourceApi>>> = _uiState

    init {
        fetchNewsSources()
    }

    private fun fetchNewsSources() {
        viewModelScope.launch {
            topHeadlinePaginationRepository.getNewsSources()
                .catch {
                    uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    uiState.value = UiState.Success(it)
                }
        }
    }
}