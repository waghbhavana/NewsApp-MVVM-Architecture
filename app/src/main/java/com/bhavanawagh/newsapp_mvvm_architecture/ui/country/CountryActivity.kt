package com.bhavanawagh.newsapp_mvvm_architecture.ui.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavanawagh.newsapp_mvvm_architecture.NewsApplication
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Country
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityCountryBinding
import com.bhavanawagh.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.bhavanawagh.newsapp_mvvm_architecture.di.module.ActivityModule
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY_LIST
import javax.inject.Inject


class CountryActivity : AppCompatActivity() {

    @Inject
    lateinit var countryAdapter: CountryAdapter

    private lateinit var binding: ActivityCountryBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        binding=ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTheUI()

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