package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.NewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<ApiArticle>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ApiArticle>>> = _uiState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            newsRepository.getTopHeadlines(AppConstants.EXTRAS_COUNTRY)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun fetchTopHeadlines(country: String) {
        println("HiltViewModel, fetchTopHeadlines")
        viewModelScope.launch {
            newsRepository.getTopHeadlines(country)
                .catch {
                    _uiState.value = UiState.Error(it.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun fetchTopHeadlinesBySource(source: String) {
        println("fetchTopHeadlinesBySource ${source.toString()}")
        viewModelScope.launch {
            newsRepository.getTopHeadlinesBySource(source.toString())
                .catch {
                    _uiState.value = UiState.Error(it.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun fetchTopHeadlinesByLanguage(language: String) {
        viewModelScope.launch {
            newsRepository.getTopHeadlinesByLanguage(language)
                .catch {
                    _uiState.value = UiState.Error(it.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun fetchTopHeadlinesBySearch(country: String, query: String) {

        viewModelScope.launch {
            newsRepository.getTopHeadlinesBySearch(country, query)
                .catch {
                    _uiState.value = UiState.Error(it.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}