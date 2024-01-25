package com.bhavanawagh.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.NewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageAdapter
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
    fun provideNewsListViewModel(topHeadlineNewsRepository: NewsRepository): TopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineNewsRepository)
            })[TopHeadlineViewModel::class.java]
    }


    @Provides
    fun provideTopHeadlineAdapter()=TopHeadlineAdapter( ArrayList())



    @Provides
    fun provideNewsSourcesViewModel(topHeadlineNewsRepository: NewsRepository): NewsSourcesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsSourcesViewModel::class) {
                NewsSourcesViewModel(topHeadlineNewsRepository)
            })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideNewsSourceViewModel()= NewsSourcesAdapter(activity,ArrayList())

    @Provides
    fun provideCountryAdapter()= CountryAdapter(activity,ArrayList())

    @Provides
    fun provideLanguageAdapter()= LanguageAdapter(activity, ArrayList())

}