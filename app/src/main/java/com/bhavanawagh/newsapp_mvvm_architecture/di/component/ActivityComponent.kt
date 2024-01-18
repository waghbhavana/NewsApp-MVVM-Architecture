package com.bhavanawagh.newsapp_mvvm_architecture.di.component

import com.bhavanawagh.newsapp_mvvm_architecture.di.ActivityScope
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourceActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class],modules = [ActivityModule::class])
interface ActivityComponent {

fun inject(activity: TopHeadlineActivity)

fun inject(activity: NewsSourceActivity)

fun inject(activity: CountryActivity)

fun inject(activity:LanguageActivity)
}

