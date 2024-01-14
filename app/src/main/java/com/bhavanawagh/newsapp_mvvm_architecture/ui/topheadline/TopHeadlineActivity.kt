package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: TopHeadlineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        injectDependencies()
        setContentView(R.layout.activity_top_headline)
        viewModel.fetchTopHeadlines(AppConstants.COUNTRY_US)
    }



    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}