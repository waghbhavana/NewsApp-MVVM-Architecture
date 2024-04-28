package com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "source")
data class Source(

    @ColumnInfo(name = "sourceId")
    val id: String?,

    @ColumnInfo(name = "sourceName")
    val name: String = ""
)
