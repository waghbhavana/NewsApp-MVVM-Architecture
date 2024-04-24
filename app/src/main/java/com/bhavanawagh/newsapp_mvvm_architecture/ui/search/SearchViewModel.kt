package com.bhavanawagh.newsapp_mvvm_architecture.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.NewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.DEBOUNCE_TIMEOUT
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.EXTRAS_COUNTRY
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.MIN_SEARCH_CHAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Success(emptyList()))

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    val _searchQuery = MutableStateFlow("")
    //val searchQuery= _searchQuery.asStateFlow()

//    val articles = _searchQuery.combine(_uiState){
//        text, article-> if(text.isBlank()){
//            article
//        }
//    }
private var _articles= MutableStateFlow<List<Article>>(emptyList())

    val articles: StateFlow<List<Article>> = _articles
    init {
        createNewsFlow()
    }

    fun onSearchTextChange(query: String) {
        Log.d("SearchViewModel", "changed query-$query")
        _searchQuery.value = query
    }

    private fun createNewsFlow() {

        viewModelScope.launch {
            _searchQuery.debounce(DEBOUNCE_TIMEOUT)
                .filter {
                    if (it.isNotEmpty() && it.length > MIN_SEARCH_CHAR)
                        return@filter true
                    else
                        _uiState.value = UiState.Success(emptyList())
                    return@filter false
                }
                .distinctUntilChanged()
                .flatMapLatest { it ->
                    _uiState.value = UiState.Loading
                    return@flatMapLatest newsRepository.getTopHeadlinesBySearch(EXTRAS_COUNTRY, it)
                        .catch { e ->
                            _uiState.value = UiState.Error(e.toString())
                        }

                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiState.value = UiState.Success(it)
                    _articles.value= it
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