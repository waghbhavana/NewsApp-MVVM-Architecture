package com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    var title: String,
    var route: String,
    val icon: ImageVector

)
