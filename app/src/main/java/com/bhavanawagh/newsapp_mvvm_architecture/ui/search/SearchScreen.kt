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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowError
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowLoading
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.ArticleList

import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenRoute(onNewsClick: (url: String) -> Unit) {
    val viewModel: SearchViewModel = hiltViewModel()
    val articles = viewModel.uiState.collectAsLazyPagingItems()
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ), title = { Text(text = AppConstants.APP_NAME) })
    }, content = { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ArticleListWithSearch(onNewsClick)
            SearchScreen(articles)
        }
    })
}


@Composable
fun SearchScreen(articles: LazyPagingItems<ApiArticle>) {

    // ArticleList(articles)
    articles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.refresh is LoadState.Error -> {
                val error = articles.loadState.refresh as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }

            loadState.append is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.append is LoadState.Error -> {
                val error = articles.loadState.append as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListWithSearch(onNewsClick: (url: String) -> Unit) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val apiArticles = viewModel.articles.collectAsLazyPagingItems()
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

