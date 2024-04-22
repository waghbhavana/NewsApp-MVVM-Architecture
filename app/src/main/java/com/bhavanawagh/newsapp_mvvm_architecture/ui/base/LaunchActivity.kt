package com.bhavanawagh.newsapp_mvvm_architecture.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.theme.NewsAppTheme
import com.bhavanawagh.newsapp_mvvm_architecture.ui.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                NewsNavHost()
            }
        }
    }
}