package com.bhavanawagh.newsapp_mvvm_architecture.ui.search

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivitySearchBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    lateinit var binding:ActivitySearchBinding
    @Inject
    lateinit var viewModel: TopHeadlineViewModel
    @Inject
    lateinit var adapter: TopHeadlineAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding=ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpObserver()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML.
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        // Get the SearchView and set the searchable configuration.
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView1=menu.findItem(R.id.actionSearch).actionView as SearchView
//        (menu.findItem(R.id.actionSearch).actionView as SearchView).apply {
//            // Assumes current activity is the searchable activity.
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//            setIconifiedByDefault(false) // Don't iconify the widget. Expand it by default.
//        }
        searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.fetchTopHeadlinesBySearch(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.fetchTopHeadlinesBySearch(query)
                return true
            }
        })

        return true
    }


    private fun setUpUI() {
        val recyclerView = binding.newsList.recyclerView
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
                            binding.newsList.progressBar.visibility = View.GONE
                            Log.d("renderList",it.data.toString())
                            renderList(it.data)
                            binding.newsList.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.newsList.progressBar.visibility = View.VISIBLE
                            binding.newsList.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.newsList.progressBar.visibility = View.GONE
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
}


