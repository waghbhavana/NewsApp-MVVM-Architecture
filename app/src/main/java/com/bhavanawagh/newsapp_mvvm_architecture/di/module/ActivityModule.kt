package com.bhavanawagh.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.Repository
import com.bhavanawagh.newsapp_mvvm_architecture.di.ActivityContext
import com.bhavanawagh.newsapp_mvvm_architecture.di.ApplicationContext
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourcesAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourcesViewModel
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context {
        return activity
    }


    @Provides
    fun provideNewsListViewModel(topHeadlineRepository: Repository): TopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineRepository)
            })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter()=TopHeadlineAdapter( ArrayList())



    @Provides
    fun provideNewsSourcesViewModel(topHeadlineRepository: Repository): NewsSourcesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsSourcesViewModel::class) {
                NewsSourcesViewModel(topHeadlineRepository)
            })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideNewsSourceViewModel()= NewsSourcesAdapter(activity,ArrayList())

}