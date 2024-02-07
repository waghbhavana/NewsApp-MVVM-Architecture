package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Article
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityTopHeadlineBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlineActivity : AppCompatActivity() {

    private lateinit var viewModel: TopHeadlineViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    lateinit var countrySelected: String

    private lateinit var binding: ActivityTopHeadlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpViewModel()
        fetchHeadlines()
        setUpObserver()
    }


    private fun fetchHeadlines() {

        if (intent.hasExtra(EXTRAS_COUNTRY)) {
            val country = intent.getStringExtra(EXTRAS_COUNTRY)
            country?.let {
                countrySelected = country
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
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
        binding.showErrorLayout.tryAgainBtn.setOnClickListener {
            if (countrySelected.isNotEmpty()) {
                viewModel.fetchTopHeadlines(countrySelected)
            } else {
                fetchHeadlines()
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[TopHeadlineViewModel::class.java]
    }


    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                            if (it.data.isNotEmpty()) {
                                renderList(it.data)
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.noDataFound.visibility = View.GONE
                            } else {
                                binding.recyclerView.visibility = View.GONE
                                binding.noDataFound.visibility = View.VISIBLE
                            }
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.noDataFound.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.VISIBLE
                            binding.noDataFound.visibility = View.GONE
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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    companion object {
        const val EXTRAS_COUNTRY = "EXTRAS_COUNTRY"
        const val EXTRAS_SOURCE = "EXTRAS_SOURCE"
        const val EXTRAS_LANGUAGE = "EXTRAS_LANGUAGE"


        fun getCountryForNewsList(context: Context, country: String, type: String) {
            context.startActivity(
                Intent(context, TopHeadlineActivity::class.java)
                    .putExtra("EXTRAS_COUNTRY", country)
                    .putExtra("COUNTRY", type)
            )
        }

        fun getLanguageForNewsList(context: Context, language: String, type: String) {
            context.startActivity(
                Intent(context, TopHeadlineActivity::class.java)
                    .putExtra("EXTRAS_LANGUAGE", language)
                    .putExtra("LANGUAGE", type)
            )
        }

        fun getSourceForNewsList(context: Context, source: String, type: String) {
            context.startActivity(
                Intent(context, TopHeadlineActivity::class.java)
                    .putExtra("EXTRAS_SOURCE", source)
                    .putExtra("SOURCE", type)
            )
        }
    }

}