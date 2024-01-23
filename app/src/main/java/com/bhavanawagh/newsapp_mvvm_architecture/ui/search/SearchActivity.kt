package com.bhavanawagh.newsapp_mvvm_architecture.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivitySearchBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineViewModel
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var viewModel: TopHeadlineViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter
    lateinit var searchQuery: String
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpObserver()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val searchView = menu.findItem(R.id.actionSearch).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchQuery = query
                }
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchQuery = query
                if (query.isNotEmpty()) {
                    adapter.notifyDataSetChanged()
                    viewModel.fetchTopHeadlinesBySearch(AppConstants.EXTRAS_COUNTRY, query)
                }
                return true
            }
        })

        return true
    }


    private fun setUpUI() {
        val recyclerView = binding.newsList.recyclerView
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
        binding.newsList.showErrorLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchTopHeadlinesBySearch(AppConstants.EXTRAS_COUNTRY, searchQuery)
        }

    }

    private fun setUpObserver() {

        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {

                        is UiState.Success -> {
                            binding.newsList.progressBar.visibility = View.GONE
                            binding.newsList.showErrorLayout.errorLayout.visibility = View.GONE
                            if(it.data.isNotEmpty()) {
                                renderList(it.data)
                                binding.newsList.recyclerView.visibility = View.VISIBLE
                                binding.newsList.noDataFound.visibility =View.GONE
                            }else{
                                binding.newsList.recyclerView.visibility = View.GONE
                                binding.newsList.noDataFound.visibility =View.VISIBLE
                            }


                        }

                        is UiState.Loading -> {
                            binding.newsList.progressBar.visibility = View.VISIBLE
                            binding.newsList.recyclerView.visibility = View.GONE
                            binding.newsList.showErrorLayout.errorLayout.visibility = View.GONE
                            binding.newsList.noDataFound.visibility =View.GONE
                        }

                        is UiState.Error -> {
                            binding.newsList.progressBar.visibility = View.GONE
                            binding.newsList.recyclerView.visibility = View.GONE
                            binding.newsList.showErrorLayout.errorLayout.visibility = View.VISIBLE
                            binding.newsList.noDataFound.visibility =View.GONE
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
    }


    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}

