package com.bhavanawagh.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.NewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryViewModel
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageViewModel
import com.bhavanawagh.newsapp_mvvm_architecture.ui.search.SearchViewModel
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
    fun provideNewsSourcesViewModel(newsRepository: NewsRepository): NewsSourcesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsSourcesViewModel::class) {
                NewsSourcesViewModel(newsRepository)
            })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideCountryViewModel(newsRepository: NewsRepository): CountryViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(CountryViewModel::class) {
                CountryViewModel(newsRepository)
            })[CountryViewModel::class.java]
    }
    @Provides
    fun provideLanguageViewModel(newsRepository: NewsRepository): LanguageViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(LanguageViewModel::class) {
                LanguageViewModel(newsRepository)
            })[LanguageViewModel::class.java]
    }
    @Provides
    fun provideSearchViewModel(newsRepository: NewsRepository): SearchViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(SearchViewModel::class) {
                SearchViewModel(newsRepository)
            })[SearchViewModel::class.java]
    }

    @Provides
    fun provideNewsSourceViewModel()= NewsSourcesAdapter(activity,ArrayList())




    @Provides
    fun provideCountryAdapter()= CountryAdapter(activity,ArrayList())

    @Provides
    fun provideLanguageAdapter()= LanguageAdapter(activity, ArrayList())

}