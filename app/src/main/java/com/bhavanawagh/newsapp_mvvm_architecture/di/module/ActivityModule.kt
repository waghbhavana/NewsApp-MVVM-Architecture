package com.bhavanawagh.newsapp_mvvm_architecture.di.module

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlinesRepository
import com.bhavanawagh.newsapp_mvvm_architecture.di.ActivityContext
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }


    @Provides
    fun provideNewsListViewModel(topHeadlineRepository: TopHeadlinesRepository): TopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineRepository)
            })[TopHeadlineViewModel::class.java]
    }




}