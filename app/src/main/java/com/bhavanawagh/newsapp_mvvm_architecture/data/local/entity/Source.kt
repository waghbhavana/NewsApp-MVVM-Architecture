package com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi

@Entity(tableName = "source")
data class Source(

    @ColumnInfo(name = "sourceId")
    val id: String?,

    @ColumnInfo(name = "sourceName")
    val name: String = ""
)

 fun Source.toSourceApi(): SourceApi {

    return SourceApi(id, name)
}