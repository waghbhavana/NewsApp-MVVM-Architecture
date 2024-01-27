package com.bhavanawagh.newsapp_mvvm_architecture.ui.country

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Country
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityCountryBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY_LIST
import kotlinx.coroutines.launch
import javax.inject.Inject


class CountryActivity : AppCompatActivity() {

    @Inject
    lateinit var countryAdapter: CountryAdapter

    private lateinit var binding: ActivityCountryBinding

    @Inject
    lateinit var countryViewModel: CountryViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        binding=ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTheUI()
        getCountryList()
        setUpObserver()
    }

    private fun getCountryList() {
        countryViewModel.fetchCountryList()
    }

    private fun setUpTheUI(){
        val recyclerView= binding.countryRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter=countryAdapter
        renderList(COUNTRY_LIST)
    }

    private fun setUpObserver() {
        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.countryProgressBar.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                            if (it.data.isNotEmpty()) {
                                renderList(it.data)
                                binding.countryRecyclerView.visibility = View.VISIBLE
                                binding.noDataFound.visibility = View.GONE
                            } else {
                                binding.countryRecyclerView.visibility = View.GONE
                                binding.noDataFound.visibility = View.VISIBLE
                            }
                        }

                        is UiState.Loading -> {
                            binding.countryProgressBar.visibility = View.VISIBLE
                            binding.showErrorLayout.errorLayout.visibility = View.GONE
                            binding.countryRecyclerView.visibility = View.GONE
                            binding.noDataFound.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.countryProgressBar.visibility = View.GONE
                            binding.countryRecyclerView.visibility = View.GONE
                            binding.showErrorLayout.errorLayout.visibility = View.VISIBLE
                            binding.noDataFound.visibility = View.GONE
                            Toast.makeText(this@CountryActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }


    private fun renderList(countryList: List<Country>){
        countryAdapter.addData(countryList)
        countryAdapter.notifyDataSetChanged()
    }
    private fun injectDependencies(){
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this@CountryActivity)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}