package com.bhavanawagh.newsapp_mvvm_architecture.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.databinding.ActivityMainBinding
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topHeadlines.setOnClickListener {
            startActivity(Intent(this, TopHeadlineActivity::class.java))
            finish()

        }
    }


}
