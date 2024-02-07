package com.bhavanawagh.newsapp_mvvm_architecture.di.module

import android.content.Context
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourcesAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {


//
//    @Provides
//    fun provideNewsListViewModel(topHeadlineNewsRepository: NewsRepository): TopHeadlineViewModel {
//        return ViewModelProvider(activity,
//            ViewModelProviderFactory(TopHeadlineViewModel::class) {
//                TopHeadlineViewModel(topHeadlineNewsRepository)
//            })[TopHeadlineViewModel::class.java]
//    }


    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())


//
//    @Provides
//    fun provideNewsSourcesViewModel(newsRepository: NewsRepository): NewsSourcesViewModel {
//        return ViewModelProvider(activity,
//            ViewModelProviderFactory(NewsSourcesViewModel::class) {
//                NewsSourcesViewModel(newsRepository)
//            })[NewsSourcesViewModel::class.java]
//    }
//
//    @Provides
//    fun provideCountryViewModel(newsRepository: NewsRepository): CountryViewModel {
//        return ViewModelProvider(activity,
//            ViewModelProviderFactory(CountryViewModel::class) {
//                CountryViewModel(newsRepository)
//            })[CountryViewModel::class.java]
//    }
//    @Provides
//    fun provideLanguageViewModel(newsRepository: NewsRepository): LanguageViewModel {
//        return ViewModelProvider(activity,
//            ViewModelProviderFactory(LanguageViewModel::class) {
//                LanguageViewModel(newsRepository)
//            })[LanguageViewModel::class.java]
//    }
//    @Provides
//    fun provideSearchViewModel(newsRepository: NewsRepository): SearchViewModel {
//        return ViewModelProvider(activity,
//            ViewModelProviderFactory(SearchViewModel::class) {
//                SearchViewModel(newsRepository)
//            })[SearchViewModel::class.java]
//    }

    @Provides
    fun provideNewsSourceViewModel(@ActivityContext activityContext: Context) =
        NewsSourcesAdapter(activityContext, ArrayList())


    @Provides
    fun provideCountryAdapter(@ActivityContext activityContext: Context) =
        CountryAdapter(activityContext, ArrayList())

    @Provides
    fun provideLanguageAdapter(@ActivityContext activityContext: Context) =
        LanguageAdapter(activityContext, ArrayList())

}