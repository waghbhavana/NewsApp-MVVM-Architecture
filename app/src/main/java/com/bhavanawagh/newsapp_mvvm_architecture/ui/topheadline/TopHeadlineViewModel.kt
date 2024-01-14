package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlinesRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineViewModel  @Inject constructor (private val topHeadlinesRepository: TopHeadlinesRepository): ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun fetchTopHeadlines(country: String){
        viewModelScope.launch {
            topHeadlinesRepository.getTopHeadlines(country)
                .catch {
                    _uiState.value = UiState.Error(it.toString())
                }
                .collect{
                    Log.d("TopHeadline",it.toString())
                    _uiState.value= UiState.Success(it)
                }
        }


    }

}