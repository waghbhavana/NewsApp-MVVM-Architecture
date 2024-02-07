package com.bhavanawagh.newsapp_mvvm_architecture.ui.language

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityLanguageBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LanguageActivity : AppCompatActivity() {
    @Inject
    lateinit var languageAdapter: LanguageAdapter


    private lateinit var languageViewModel: LanguageViewModel

    private lateinit var binding: ActivityLanguageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTheUI()
        setUpViewModel()
        fetchLanguageList()
        setUpObserver()

    }

    private fun fetchLanguageList() {
        languageViewModel.fetchLanguageList()
    }

    private fun setUpTheUI() {
        val recyclerView = binding.languageRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = languageAdapter
        renderList(AppConstants.LANGUAGE_LIST)
    }

    private fun setUpViewModel() {
        languageViewModel = ViewModelProvider(this)[LanguageViewModel::class.java]
    }

    private fun renderList(languageList: List<Language>) {
        languageAdapter.addData(languageList)
        languageAdapter.notifyDataSetChanged()
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languageViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.languageProgressBar.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                            if (it.data.isNotEmpty()) {
                                renderList(it.data)
                                binding.languageRecyclerView.visibility = View.VISIBLE
                                binding.noDataFound.visibility = View.GONE
                            } else {
                                binding.languageRecyclerView.visibility = View.GONE
                                binding.noDataFound.visibility = View.VISIBLE
                            }
                        }

                        is UiState.Loading -> {
                            binding.languageProgressBar.visibility = View.VISIBLE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                            binding.languageRecyclerView.visibility = View.GONE
                            binding.noDataFound.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.languageProgressBar.visibility = View.GONE
                            binding.languageRecyclerView.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.VISIBLE
                            binding.noDataFound.visibility = View.GONE
                            Toast.makeText(this@LanguageActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}