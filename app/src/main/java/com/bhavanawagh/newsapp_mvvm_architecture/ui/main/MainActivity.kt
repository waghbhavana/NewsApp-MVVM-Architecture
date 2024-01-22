package com.bhavanawagh.newsapp_mvvm_architecture.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityMainBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.country.CountryActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.language.LanguageActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.search.SearchActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.source.NewsSourceActivity
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topHeadlines.setOnClickListener {
         startActivity(Intent(this, TopHeadlineActivity::class.java)
               .putExtra("EXTRAS_COUNTRY",AppConstants.EXTRAS_COUNTRY))


        }
        binding.newsSources.setOnClickListener{
            startActivity(Intent(this,NewsSourceActivity::class.java))

        }
        binding.countries.setOnClickListener{
            startActivity(Intent(this,CountryActivity::class.java))

        }
        binding.language.setOnClickListener{
            startActivity(Intent(this,LanguageActivity::class.java))

        }
        binding.search.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
