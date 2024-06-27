package com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryScreenRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageScreenRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.search.SearchScreenRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourcesRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadlinePagination.TopHeadlineRouteBy
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

@Composable
fun NewsNavHost(navController: NavHostController) {

    val context = LocalContext.current

    NavHost(navController = navController, startDestination = NavigationScreen.Home.route) {

        composable(
            route = NavigationScreen.Home.route,
            arguments = listOf(
                navArgument("country") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("language") {
                    type = NavType.StringType
                    defaultValue = ""
                }, navArgument("source") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { it ->

            val country: String = it.arguments?.getString("country", "") ?: ""
            val source: String = it.arguments?.getString("source", "") ?: ""
            val language: String = it.arguments?.getString("language", "") ?: ""

            if (source.isNotEmpty()) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                }, source, "source")

            } else if (language.isNotEmpty()) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                }, language, "language")
            } else if (country.isNotEmpty()) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                }, country, "country")
            } else if (source.isEmpty() && language.isEmpty() && country.isEmpty()) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                }, AppConstants.EXTRAS_COUNTRY, "country")
            }
        }

        composable(route = NavigationScreen.Search.route) {
            SearchScreenRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

        composable(route = NavigationScreen.Country.route) {
            CountryScreenRoute(onCountryClick = {
                println("CountryScreenRoute : $it")
                navController.navigate("$HomeScreenPath?country=$it") {
                    launchSingleTop = true
                }

            })
        }

        composable(route = NavigationScreen.Language.route) {
            LanguageScreenRoute(onLanguageClick = {
                println("LanguageScreenRoute : $it")
                navController.navigate("$HomeScreenPath?language=$it") {
                    launchSingleTop = true
                }
            })
        }
        composable(route = NavigationScreen.Source.route) {
            NewsSourcesRoute(onSourceClick = {
                println("NewsSourcesRoute : $it")
                navController.navigate("$HomeScreenPath?source=$it") {
                    launchSingleTop = true
                }
            })
        }


    }

}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}
