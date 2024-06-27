package com.bhavanawagh.newsapp_mvvm_architecture.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation.NavigationScreen
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation.NewsNavHost
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

enum class ButtonLabel(name: String) {
    HEADLINE("Top Headlines"),
    SOURCES("News Sources"),
    COUNTRIES("Countries"),
    LANGUAGES("Languages"),
    SEARCH("Search"),
    OFFLINEHEADLINE("Offline Headlines"),
    HEADLINEPAGINATION("Headlines Pagination")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, onClick: (label: String) -> Unit) {
    val mainNavController = rememberNavController()
    Scaffold(bottomBar = {
        BottomAppBar(modifier = Modifier) {
           // BottomNavigationBar(navController = navController)
        }
    },
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ), title = { Text(text = AppConstants.APP_NAME) })
        }, content =

        { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            )
//            {
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp), onClick = {
//                    navController.navigate(Route.Headline.name)
//                }) { Text(text = ButtonLabel.HEADLINE.name) }
//
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp), onClick = {
//                    // println("News Sources button clicked")
//                    navController.navigate(Route.Sources.name)
//
//                }) { Text(text = ButtonLabel.SOURCES.name) }
//
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp), onClick = {
//                    navController.navigate(Route.Country.name)
//                }) { Text(ButtonLabel.COUNTRIES.name) }
//
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp), onClick = {
//                    navController.navigate(Route.Language.name)
//                }) { Text(ButtonLabel.LANGUAGES.name) }
//
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp), onClick = {
//                    navController.navigate(Route.Search.name)
//                }) { Text(ButtonLabel.SEARCH.name) }
//
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp), onClick = {
//                    navController.navigate(Route.HeadlinesPagination.name)
//                }) {
//                    Text(ButtonLabel.HEADLINEPAGINATION.name)
//                }
//
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp), onClick = {
//                    navController.navigate(Route.OfflineHeadlines.name)
//                }) { Text(ButtonLabel.OFFLINEHEADLINE.name) }
//
//
//            }
            {
                NewsNavHost(mainNavController)
            }

        }
    )
}
//
//    @Composable
//    fun BottomNavigationBar(navController: NavController) {
//        val items = listOf(
//            NavigationScreen.Home,
//            NavigationScreen.Source,
//            NavigationScreen.Language,
//            NavigationScreen.Country,
//            NavigationScreen.Search,
//        )
//        var selectedItem by remember { mutableStateOf(0) }
//        var currentRoute by remember { mutableStateOf(NavigationScreen.Home.route) }
//
//        items.forEachIndexed { index, navigationItem ->
//            if (navigationItem.route == currentRoute) {
//                selectedItem = index
//            }
//        }
//
//        NavigationBar {
//            items.forEachIndexed { index, item ->
//                NavigationBarItem(
//                    alwaysShowLabel = true,
//                    icon = { Icon(item.icon!!, contentDescription = item.title) },
//                    label = { Text(item.title) },
//                    selected = selectedItem == index,
//                    onClick = {
//                        selectedItem = index
//                        currentRoute = item.route
//                        navController.navigate(item.route) {
//                            navController.graph.startDestinationRoute?.let { route ->
//                                popUpTo(route) {
//                                    saveState = true
//                                }
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                )
//            }
//        }
//    }

@Composable
fun Navigations(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationScreen.Home.route) {
        composable(NavigationScreen.Home.route) {
            //HomeScreen()
        }
        composable(NavigationScreen.Source.route) {
           // HistoryScreen()
        }
        composable(NavigationScreen.Language.route) {
           // ProfileScreen()
        }
    }
}



