package com.bhavanawagh.newsapp_mvvm_architecture.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.Route
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

enum class ButtonLabel(name: String) {
    HEADLINE("Top Headlines"),
    SOURCES("News Sources"),
    COUNTRIES("Countries"),
    LANGUAGES("Languages"),
    SEARCH("Search"),
    OFFLINEHEADLINE("Offline Headlines")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, onClick: (label: String) -> Unit) {

    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ), title = { Text(text = AppConstants.APP_NAME) })
    }, content = { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), onClick = {
                navController.navigate("Top Headlines")
            }) {
                Text(text = ButtonLabel.HEADLINE.name)
            }


            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), onClick = {
                navController.navigate(Route.Sources.name)
            }) { Text(text = ButtonLabel.SOURCES.name) }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), onClick = {
                navController.navigate(Route.Country.name)
            }) { Text(ButtonLabel.COUNTRIES.name) }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), onClick = {
                navController.navigate(Route.Language.name)
            }) { Text(ButtonLabel.LANGUAGES.name) }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), onClick = {
                navController.navigate(Route.Search.name)
            }) { Text(ButtonLabel.SEARCH.name) }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), onClick = {
                navController.navigate(Route.OfflineHeadlines.name)
            }) { Text(ButtonLabel.OFFLINEHEADLINE.name) }
//
//            Button(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), onClick = {
//                navController.navigate(Route.Search.name)
//            }) { Text(ButtonLabel.SEARCH.name)}
        }

    })
}


