package com.bhavanawagh.newsapp_mvvm_architecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.dao.ArticleDao
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article


@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}