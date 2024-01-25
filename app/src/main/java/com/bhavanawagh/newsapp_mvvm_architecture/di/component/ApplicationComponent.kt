package com.bhavanawagh.newsapp_mvvm_architecture.di.component

import android.content.Context
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.NewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.di.ApplicationContext
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)


    @ApplicationContext
    fun getContext(): Context


    fun getNetworkService(): NetworkService

    fun getTopHeadlineRepository(): NewsRepository
}