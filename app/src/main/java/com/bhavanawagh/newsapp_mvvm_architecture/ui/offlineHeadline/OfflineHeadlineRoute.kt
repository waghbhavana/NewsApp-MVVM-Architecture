package com.bhavanawagh.newsapp_mvvm_architecture.ui.offlineHeadline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowError
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowLoading

@Composable
fun OfflineHeadlineRoute(onNewsClick: (url: String) -> Unit) {

    val offlineHeadlineViewModel: OfflineHeadlineViewModel = hiltViewModel()
    val articles = offlineHeadlineViewModel.articles.collectAsLazyPagingItems()
    OfflineHeadlineScreen(articles, onNewsClick)
}

@Composable
fun OfflineHeadlineScreen(
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

@Composable
fun ArticleList(articles: LazyPagingItems<ApiArticle>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles.itemCount, key = { index -> articles[index]!!.url }) { index ->
            Article(article = articles[index]!!, onNewsClick)
        }
    }
}

@Composable
fun Article(article: ApiArticle, onNewsClick: (url: String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            article.url.let { onNewsClick(article.url) }
        }) {
        BannerImage(article)
        article.title.let { TitleText(it) }
        DescriptionText(article.description)
        SourceText(article.sourceApi)
    }

}

@Composable
fun BannerImage(article: ApiArticle) {
    AsyncImage(
        model = article.imageUrl,
        contentDescription = article.title,
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
fun SourceText(source: SourceApi) {
    Text(
        text = source.name,
        style = MaterialTheme.typography.titleSmall,
        color = Color.Gray,
        maxLines = 1,
        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
    )
}
