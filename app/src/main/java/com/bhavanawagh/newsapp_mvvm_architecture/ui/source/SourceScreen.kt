package com.bhavanawagh.newsapp_mvvm_architecture.ui.source

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Source
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowError
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ShowLoading
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSourcesRoute(onSourceClick: (source: String) -> Unit) {
    val viewModel: NewsSourcesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ), title = { Text(text = AppConstants.APP_NAME) })
    }, content = { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NewsSourcesScreen(uiState, onSourceClick)
        }
    })

}

@Composable
fun NewsSourcesScreen(uiState: UiState<List<Source>>, onSourceClick: (url: String) -> Unit) {

    when (uiState) {
        is UiState.Success -> {
            SourceList(uiState.data, onSourceClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(uiState.message)
        }
    }
}

@Composable
fun SourceList(list: List<Source>, onSourceClick: (source: String) -> Unit) {

    LazyColumn() {
        items(list, key = { item: Source -> item.id }) {
            SourceC(it, onSourceClick)
        }
    }
}

@Composable
fun SourceC(source: Source, onSourceClick: (source: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (source.id.isNotEmpty()) {
                    onSourceClick(source.id)
                }
            }
    ) {
        Text(
            text = source.name,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            textAlign = TextAlign.Center,

        )
    }
}




