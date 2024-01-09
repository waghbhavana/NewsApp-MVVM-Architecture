package com.bhavanawagh.newsapp_mvvm_architecture.di.module

import android.app.Activity
import android.content.Context
import com.bhavanawagh.newsapp_mvvm_architecture.di.ActivityContext
import com.bhavanawagh.newsapp_mvvm_architecture.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val activity: Activity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @ActivityContext
    @Provides
    fun provideMainViewModel(): MainViewModel{
        return MainViewModel()
    }



}