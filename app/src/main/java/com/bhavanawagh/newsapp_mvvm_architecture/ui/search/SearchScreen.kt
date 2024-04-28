package com.bhavanawagh.newsapp_mvvm_architecture.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowError
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowLoading
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.ArticleList

import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenRoute(onNewsClick: (url: String) -> Unit) {
    val viewModel: SearchViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ), title = { Text(text = AppConstants.APP_NAME) })
    }, content = { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ArticleListWithSearch(onNewsClick)
            SearchScreen(uiState = uiState)
        }
    })
}


@Composable
fun SearchScreen(uiState: UiState<List<ApiArticle>>) {
    when (uiState) {

        is UiState.Success -> {
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(uiState.message)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListWithSearch(onNewsClick: (url: String) -> Unit) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchQuery by viewModel._searchQuery.collectAsState()
    val apiArticles: List<ApiArticle> by viewModel.articles.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
    )
    {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            value = searchQuery,
            onValueChange = viewModel::onSearchTextChange,
            placeholder = { Text(text = "Search") })

        Spacer(modifier = Modifier.height(4.dp))
        ArticleList(apiArticles, onNewsClick)
    }
}
