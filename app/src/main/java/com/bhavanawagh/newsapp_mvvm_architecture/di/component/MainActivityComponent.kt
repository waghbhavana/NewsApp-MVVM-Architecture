package com.bhavanawagh.newsapp_mvvm_architecture.di.component

import android.content.Context
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.di.ActivityScope
import com.bhavanawagh.newsapp_mvvm_architecture.di.ApplicationContext
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.MainActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.ui.main.MainActivity
import dagger.Component


@ActivityScope
@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

}

