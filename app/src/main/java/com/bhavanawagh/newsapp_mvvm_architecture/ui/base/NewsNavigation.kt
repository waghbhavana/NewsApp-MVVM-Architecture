package com.bhavanawagh.newsapp_mvvm_architecture.ui.base

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bhavanawagh.newsapp_mvvm_architecture.ui.main.MainScreen
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
fun NewsNavHost(){

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "Main" ){
        composable(route =  Route.Main.name){
            MainScreen(navController){

            }
        }
        composable(route="Top Headlines/{label}", arguments = listOf(
            navArgument("label"){
                type = NavType.StringType
            }
        )){ it ->
            val label : String? = it.arguments!!.getString("label")
            println("Nav host: $label")
            if (label != null) {
                TopHeadlineRouteBy(onNewsClick = {
                    openCustomChromeTab(context, it)
                },label)
            }
        }


        composable(route= Route.Headline.name){
           // navController.navigate("Top Headlines")
            TopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

        composable(route = "News Sources"){
            NewsSourcesRoute(onSourceClick = {
                println("NewsSourcesRoute : $it")
                navController.navigate("Top Headlines/$it")
            })
        }

    }


}
fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}
