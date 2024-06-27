package com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation

const val HomeScreenPath = "home"

sealed class NavigationScreen(var route: String) {

    object Home :
        NavigationScreen("$HomeScreenPath?language={language}&source={source}&country={country}")

    object Source : NavigationScreen("Source")
    object Language : NavigationScreen("Language")
    object Country : NavigationScreen("Country")
    object Search : NavigationScreen("Search")
}
