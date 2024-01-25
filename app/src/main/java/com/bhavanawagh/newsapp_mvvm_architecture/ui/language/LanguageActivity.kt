package com.bhavanawagh.newsapp_mvvm_architecture.ui.language

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityLanguageBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import javax.inject.Inject

class LanguageActivity : AppCompatActivity() {
    @Inject
    lateinit var languageAdapter: LanguageAdapter

    private lateinit var binding: ActivityLanguageBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding= ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTheUI()

    }

    private fun setUpTheUI(){
        val recyclerView= binding.languageRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter=languageAdapter
        renderList(AppConstants.LANGUAGE_LIST)
    }

    private fun renderList(languageList: List<Language>){
        languageAdapter.addData(languageList)
        languageAdapter.notifyDataSetChanged()
    }
    private fun injectDependencies(){
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this@LanguageActivity)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}