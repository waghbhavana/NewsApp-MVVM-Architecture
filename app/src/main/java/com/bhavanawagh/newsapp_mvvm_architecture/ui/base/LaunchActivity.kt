package com.bhavanawagh.newsapp_mvvm_architecture.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation.BottomNavigationBar
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation.NavigationItem
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation.NavigationScreen
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation.NewsNavHost
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.theme.NewsAppTheme
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NewsAppTheme {
                val items = listOf(
                    NavigationItem(
                        title = "News",
                        route = NavigationScreen.Home.route,
                        icon = ImageVector.vectorResource(R.drawable.baseline_newspaper_24)
                    ),
                    NavigationItem(
                        title = "Source",
                        route = NavigationScreen.Source.route,
                        icon = ImageVector.vectorResource(R.drawable.baseline_source_24)
                    ),
                    NavigationItem(
                        title = "Language",
                        route = NavigationScreen.Language.route,
                        icon = ImageVector.vectorResource(R.drawable.baseline_language_24)
                    ),
                    NavigationItem(
                        title = "Country",
                        route = NavigationScreen.Country.route,
                        icon = ImageVector.vectorResource(R.drawable.baseline_location_on_24)
                    ),
                    NavigationItem(
                        title = "Search",
                        route = NavigationScreen.Search.route,
                        icon = ImageVector.vectorResource(R.drawable.baseline_search_24)
                    )

                )
                val navController: NavHostController = rememberNavController()
                val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val topBarTitle: String = if (currentRoute != null) {
                    items[items.indexOfFirst {
                        it.route == currentRoute
                    }].title
                } else {
                    items[0].title

                }

                Scaffold(
                    topBar = {
                        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Purple40,
                            titleContentColor = Color.White
                        ),

                            title = { Text(text = topBarTitle) })
                    },
                    bottomBar = {
                        BottomNavigationBar(items = items, currentRoute = currentRoute) {
                            navController.navigate(it.route) {
                                navController.graph.startDestinationRoute?.let { startDestinationRoute: String ->
                                    popUpTo(startDestinationRoute) {
                                        saveState = true
                                    }
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                ) {
                    it
                    NewsNavHost(navController)
                }

            }
        }
    }
}
