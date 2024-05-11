package com.bhavanawagh.newsapp_mvvm_architecture.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlinePaginationRepository
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.DEBOUNCE_TIMEOUT
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.MIN_SEARCH_CHAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val topHeadlinePaginationRepository: TopHeadlinePaginationRepository) :
    ViewModel() {


    private val _uiState = MutableStateFlow<PagingData<ApiArticle>>(PagingData.empty())

    val uiState: StateFlow<PagingData<ApiArticle>> = _uiState

    val searchQuery = MutableStateFlow("")

    private var _articles = MutableStateFlow<PagingData<ApiArticle>>(PagingData.empty())

    val articles: StateFlow<PagingData<ApiArticle>> = _articles

    init {
        createNewsFlow()
    }

    fun onSearchTextChange(query: String) {
        Log.d("SearchViewModel", "changed query-$query")
        searchQuery.value = query
    }

    private fun createNewsFlow() {

        viewModelScope.launch {
            searchQuery.debounce(DEBOUNCE_TIMEOUT)
                .filter {
                    if (it.isNotEmpty() && it.length > MIN_SEARCH_CHAR)
                        return@filter true
                    else
                        _uiState.value = PagingData.empty()
                    return@filter false
                }
                .distinctUntilChanged()
                .flatMapLatest { it ->
                    _uiState.value = PagingData.empty()
                    return@flatMapLatest topHeadlinePaginationRepository.getTopHeadlinesBySearch(it)
                        .cachedIn(viewModelScope)

                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiState.value = it
                    _articles.value = it
                }
        }

    }

    fun fetchTopHeadlinesBySearch(query: String) {

        viewModelScope.launch {
            topHeadlinePaginationRepository.getTopHeadlinesBySearch(query).cachedIn(viewModelScope)

                .collect {
                    _uiState.value = it
                }
        }
    }


}