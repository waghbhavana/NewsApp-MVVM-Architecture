package com.bhavanawagh.newsapp_mvvm_architecture.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article


@Dao
interface ArticleDao {


    @Query("SELECT * FROM article")
    fun getAll(): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    @Query("DELETE FROM article")
    fun deleteAll()

    @Transaction
    fun deleteAndInsertAll(articles: List<Article>) {
        deleteAll()
        insertAll(articles)
    }


}