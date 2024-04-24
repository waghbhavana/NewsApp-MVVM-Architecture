package com.bhavanawagh.newsapp_mvvm_architecture.ui.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryScreenRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageScreenRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.main.MainScreen
import com.bhavanawagh.newsapp_mvvm_architecture.ui.search.SearchScreenRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourcesRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineRoute
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineRouteBy
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineScreen

sealed class Route(val name: String) {
    object Main : Route("Main")
    object Headline : Route("Top Headlines")
    object Sources : Route("News Sources")
    object Language : Route("Language")
    object Country : Route("Country")
    object Search : Route("Search")
}


@Composable
fun NewsNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "Main") {
        composable(route = Route.Main.name) {
            MainScreen(navController){ }
        }

        composable(route = "Top Headlines/language/{language}", arguments = listOf(
            navArgument("language") {
                type = NavType.StringType
            })) { it ->
            val language: String? = it.arguments!!.getString("language")
            if (language != null) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                }, language, "language")
            }
        }

        composable(route = "Top Headlines/source/{source}", arguments = listOf(
            navArgument("source") {
                type = NavType.StringType
            })) { it ->
            val source: String? = it.arguments!!.getString("source")
            if (source != null) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                }, source, "source")
            }
        }
        composable(route = "Top Headlines/country/{country}", arguments = listOf(
            navArgument("country") {
                type = NavType.StringType
            })) { it ->
            val country: String? = it.arguments!!.getString("country")
            if (country != null) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                }, country, "country")
            }
        }

        composable(route = Route.Search.name) {
            SearchScreenRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

        composable(route = Route.Headline.name) {
            TopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

        composable(route = Route.Country.name) {
            CountryScreenRoute(onCountryClick = {
                println("CountryScreenRoute : $it")
                navController.navigate("Top Headlines/country/${it}")
            })
        }

        composable(route = Route.Language.name) {
            LanguageScreenRoute(onLanguageClick = {
                println("LanguageScreenRoute : $it")
                navController.navigate("Top Headlines/language/${it}")
            })
        }
        composable(route = Route.Sources.name) {
            NewsSourcesRoute(onSourceClick = {
                println("NewsSourcesRoute : $it")
                navController.navigate("Top Headlines/source/${it}")
            })
        }

    }

}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}
