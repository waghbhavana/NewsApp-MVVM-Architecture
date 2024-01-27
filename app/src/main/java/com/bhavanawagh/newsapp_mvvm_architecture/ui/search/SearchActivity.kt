package com.bhavanawagh.newsapp_mvvm_architecture.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.NewsRepository
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivitySearchBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity() : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    @Inject
    lateinit var newsRepository: NewsRepository

    lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpObserver()

    }


    private fun setUpUI() {
        val recyclerView = binding.newsList.recyclerView
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
        binding.newsList.showErrorLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchTopHeadlinesBySearch(AppConstants.EXTRAS_COUNTRY, searchQuery)
        }
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SearchViewActivity", "changed query-${newText}")
                viewModel.searchNews(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        })

    }

    private fun setUpObserver() {

        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {

                        is UiState.Success -> {
                            binding.newsList.progressBar.visibility = View.GONE
                            binding.newsList.showErrorLayout.errorLayout.visibility = View.GONE
                            if (it.data.isNotEmpty()) {
                                renderList(it.data)
                                binding.newsList.recyclerView.visibility = View.VISIBLE
                                binding.newsList.noDataFound.visibility = View.GONE
                            }
                        }

                        is UiState.Loading -> {
                            binding.newsList.progressBar.visibility = View.VISIBLE
                            binding.newsList.recyclerView.visibility = View.GONE
                            binding.newsList.showErrorLayout.errorLayout.visibility = View.GONE
                            binding.newsList.noDataFound.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.newsList.progressBar.visibility = View.GONE
                            binding.newsList.recyclerView.visibility = View.GONE
                            binding.newsList.showErrorLayout.errorLayout.visibility = View.VISIBLE
                            binding.newsList.noDataFound.visibility = View.GONE
                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_SHORT)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


