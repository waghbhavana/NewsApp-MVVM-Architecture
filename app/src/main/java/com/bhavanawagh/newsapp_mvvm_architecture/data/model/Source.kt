package com.bhavanawagh.newsapp_mvvm_architecture

import com.google.gson.annotations.SerializedName


data class Source (

  @SerializedName("id"   ) val id   : String = "",
  @SerializedName("name" ) val name : String = ""

)