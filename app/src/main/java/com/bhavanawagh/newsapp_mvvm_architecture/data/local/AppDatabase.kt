package com.bhavanawagh.newsapp_mvvm_architecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.dao.ArticleDao
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.dao.RemoteKeysDao
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.ArticleRemoteKeys


@Database(entities = [Article::class, ArticleRemoteKeys::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}