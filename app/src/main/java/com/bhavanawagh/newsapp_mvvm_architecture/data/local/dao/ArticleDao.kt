package com.bhavanawagh.newsapp_mvvm_architecture.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {


    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAll(articles: List<Article>)

    @Query("DELETE FROM article")
    fun deleteAll()

    @Transaction
    fun deleteAndInsertAll(articles: List<Article>) {
        deleteAll()
        insetAll(articles)
    }


}