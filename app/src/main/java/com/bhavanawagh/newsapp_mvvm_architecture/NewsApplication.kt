package com.bhavanawagh.newsapp_mvvm_architecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {

//    lateinit var applicationComponent: ApplicationComponent
//
//    override fun onCreate() {
//        super.onCreate()
//        injectDependencies()
//    }
//
//    private fun injectDependencies(){
//        applicationComponent=DaggerApplicationComponent.builder()
//            .applicationModule(ApplicationModule(this))
//            .build()
//        applicationComponent.inject(this)
//
//    }
}