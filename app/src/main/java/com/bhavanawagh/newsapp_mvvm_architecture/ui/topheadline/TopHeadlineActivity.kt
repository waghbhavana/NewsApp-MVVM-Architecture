package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityTopHeadlineBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var viewModel: TopHeadlineViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private lateinit var binding: ActivityTopHeadlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        fetchHeadlines()
        setUpObserver()
    }

    private fun fetchHeadlines() {

        if (intent.hasExtra(EXTRAS_COUNTRY)) {
            val country = intent.getStringExtra(EXTRAS_COUNTRY)
            country?.let {
                viewModel.fetchTopHeadlines(it)
            }
        }
        if (intent.hasExtra(EXTRAS_SOURCE)) {
            val source = intent.getStringExtra(EXTRAS_SOURCE)
            source?.let {
                viewModel.fetchTopHeadlinesBySource(it)
            }
        }
        if (intent.hasExtra(EXTRAS_LANGUAGE)) {
            val source = intent.getStringExtra(EXTRAS_LANGUAGE)
            source?.let {
                viewModel.fetchTopHeadlinesByLanguage(it)
            }
        }
    }

    private fun setUpUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setUpObserver() {
        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }


                    }
                }
            }
        }
    }

    private fun renderList(list: List<Article>) {
        adapter.addArticleData(list)
        adapter.notifyDataSetChanged()
    }


    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }


    companion object {

        const val EXTRAS_COUNTRY = "EXTRAS_COUNTRY"
        const val EXTRAS_SOURCE = "EXTRAS_SOURCE"
        const val EXTRAS_LANGUAGE = "EXTRAS_LANGUAGE"

    }

}