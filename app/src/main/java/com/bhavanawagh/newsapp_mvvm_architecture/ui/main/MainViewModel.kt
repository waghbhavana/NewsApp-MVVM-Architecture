package com.bhavanawagh.newsapp_mvvm_architecture.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bhavanawagh.newsapp_mvvm_architecture.R
import javax.inject.Inject

class MainViewModel  @Inject constructor (): ViewModel() {



   fun onButtonClicked(buttonName: String) {

       when (buttonName) {
           "Top Headlines" -> Log.d("MainViewmodel", "Button click " + buttonName)
           "News Sources" -> Log.d("MainViewmodel", "Button click " + buttonName)
           "Countries" -> Log.d("MainViewmodel", "Button click " + buttonName)
           "Languages" -> Log.d("MainViewmodel", "Button click " + buttonName)
           "Search" -> Log.d("MainViewmodel", "Button click " + buttonName)
           else -> { // Note the block
               Log.d("MainViewmodel", "Button click ")
           }
       }
   }

}