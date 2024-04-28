package com.bhavanawagh.newsapp_mvvm_architecture.data.model

import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Source
import com.google.gson.annotations.SerializedName

data class SourceApi(

    @SerializedName("id")
    val id: String? = "",

    @SerializedName("name")
    val name: String = ""

)

public fun SourceApi.toSourceEntity(): Source {

    return Source(id, name)
}