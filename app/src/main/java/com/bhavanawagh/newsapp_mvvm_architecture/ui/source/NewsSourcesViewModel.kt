package com.bhavanawagh.newsapp_mvvm_architecture.ui.source

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavanawagh.newsapp_mvvm_architecture.Source
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.Repository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsSourcesViewModel(private val repository: Repository) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
    val uiState: MutableStateFlow<UiState<List<Source>>> = _uiState


    fun fetchNewsSources() {
        viewModelScope.launch {
            repository.getNewsSources()
                .catch {
                    uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    uiState.value = UiState.Success(it)
                }
        }
    }
}