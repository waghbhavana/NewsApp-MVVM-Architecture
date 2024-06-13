package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadlinePagination

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowLoading
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopHeadlinePaginationRoute(onNewsClick: (url: String) -> Unit) {
//
//    val viewModel: TopHeadlinePaginationViewModel = hiltViewModel()
//    val uiState = viewModel.uiState.collectAsLazyPagingItems()
//
//    Scaffold(topBar = {
//        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primary,
//            titleContentColor = Color.White
//        ), title = { Text(text = AppConstants.APP_NAME) })
//    }, content = { padding ->
//        Column(modifier = Modifier.padding(padding)) {
//           // TopHeadlinePaginationScreen(uiState, onNewsClick)
//           // TopHeadlineRouteBy(onNewsClick, label = , category = )
//        }
//    })
//
//}

lateinit var topHeadlinePaginationViewModel: TopHeadlinePaginationViewModel
lateinit var ilabel: String


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlineRouteBy(onNewsClick: (url: String) -> Unit, label: String, category: String) {

    topHeadlinePaginationViewModel = hiltViewModel()
    ilabel = label

    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ), title = { Text(text = AppConstants.APP_NAME) })
    }, content = { padding ->
        Column(modifier = Modifier.padding(padding)) {

            val articles = topHeadlinePaginationViewModel.articles.collectAsLazyPagingItems()

            if (category == "source") {
                topHeadlinePaginationViewModel.fetchTopHeadlinesBySource(label)
            }
            if (category == "language") {
                topHeadlinePaginationViewModel.fetchTopHeadlinesByLanguage(label)
            }
            if (category == "country") {
                topHeadlinePaginationViewModel.fetchTopHeadlinesByCountry(label)
            }

            TopHeadlinePaginationScreen(articles, onNewsClick)
        }
    })
}


@Composable
fun TopHeadlinePaginationScreen(
    articles: LazyPagingItems<ApiArticle>,
    onNewsClick: (url: String) -> Unit
) {

    ArticleList(articles, onNewsClick)
    articles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.refresh is LoadState.Error -> {
                val error = articles.loadState.refresh as LoadState.Error
                if (error.error.localizedMessage.isNotEmpty()) {
                    TryAgainComposable(onRetry = {
                        topHeadlinePaginationViewModel.fetchTopHeadlinesByCountry(ilabel)
                    })
                } else {
                    Text(text = "An unexpected error occurred.")
                }
            }

            loadState.append is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.append is LoadState.Error -> {
                val error = articles.loadState.append as LoadState.Error
                if (error.error.localizedMessage.isNotEmpty()) {
                    TryAgainComposable(onRetry = {
                        topHeadlinePaginationViewModel.fetchTopHeadlinesByCountry(ilabel)
                    })
                } else {
                    // Handle other types of errors (optional)
                    Text(text = "An unexpected error occurred.")
                }
            }
        }
    }
}


@Composable
fun ArticleList(apiArticles: LazyPagingItems<ApiArticle>, onNewsClick: (url: String) -> Unit) {
    if (apiArticles.itemCount == 0) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            text = "No news available for now!"
        )
    } else {
        LazyColumn {
            items(apiArticles.itemCount, key = { index -> apiArticles[index]!!.url }) { index ->
                Article(apiArticles[index]!!, onNewsClick)
            }
        }
    }
}

@Composable
fun Article(apiArticle: ApiArticle, onNewsClick: (url: String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            if (apiArticle.url.isNotEmpty()) {
                onNewsClick(apiArticle.url)
            }
        }) {
        BannerImage(apiArticle)
        TitleText(apiArticle.title)
        DescriptionText(apiArticle.description)
        SourceText(apiArticle.sourceApi)
    }

}

@Composable
fun BannerImage(apiArticle: ApiArticle) {
    AsyncImage(
        model = apiArticle.imageUrl,
        contentDescription = apiArticle.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    )
}

@Composable
fun TitleText(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            maxLines = 2,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun DescriptionText(description: String?) {
    if (!description.isNullOrEmpty()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            maxLines = 2,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun SourceText(sourceApi: SourceApi) {
    Text(
        text = sourceApi.name,
        style = MaterialTheme.typography.titleSmall,
        color = Color.Gray,
        maxLines = 1,
        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
    )
}

@Composable
fun TryAgainComposable(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No internet connection!",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,

                )
            Button(onClick = onRetry) {
                Text("Try Again")
            }
        }
    }
}
