package com.bhavanawagh.newsapp_mvvm_architecture.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity() {

   lateinit var topHeadlineBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topHeadlineBtn= findViewById(R.id.top_headlines)
        topHeadlineBtn.setOnClickListener {
            startActivity(Intent(this, TopHeadlineActivity::class.java))
            finish()

        }
    }


}
