package com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleRemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val articleKey: Int,
    val prevKey: Int?,
    val nextKey: Int?
)


