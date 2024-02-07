package com.bhavanawagh.newsapp_mvvm_architecture.ui.source

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Source
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityNewsSourceBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsSourceActivity : AppCompatActivity() {


    private lateinit var newsSourcesViewModel: NewsSourcesViewModel

    @Inject
    lateinit var newsSourcesAdapter: NewsSourcesAdapter

    lateinit var binding: ActivityNewsSourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityNewsSourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpViewModel()
        newsSourcesViewModel.fetchNewsSources()
        setUpObserver()

    }

    private fun setUpUI() {
        val recyclerView = binding.recyclerView2
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = newsSourcesAdapter
        binding.showErrorLayout.tryAgainBtn.setOnClickListener {
            newsSourcesViewModel.fetchNewsSources()
        }
    }

    private fun setUpViewModel() {
        newsSourcesViewModel = ViewModelProvider(this)[NewsSourcesViewModel::class.java]
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourcesViewModel.uiState.collect {
                    when (it) {
                        is UiState.Loading -> {
                            binding.progressBar2.visibility = View.VISIBLE
                            binding.recyclerView2.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.progressBar2.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView2.visibility = View.VISIBLE
                        }

                        is UiState.Error -> {
                            binding.progressBar2.visibility = View.GONE
                            binding.recyclerView2.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.VISIBLE
                            binding.progressBar2.visibility = View.GONE
                        }

                    }
                }
            }
        }
    }

    private fun renderList(sourceList: List<Source>) {
        newsSourcesAdapter.addData(sourceList)
        newsSourcesAdapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}