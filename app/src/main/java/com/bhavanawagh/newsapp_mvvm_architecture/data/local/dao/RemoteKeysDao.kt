package com.bhavanawagh.newsapp_mvvm_architecture.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bhavanawagh.newsapp_mvvm_architecture.data.local.entity.ArticleRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM ArticleRemoteKeys WHERE articleKey=:articleKey")
    suspend fun getRemoteKeys(articleKey: Int): ArticleRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllArticleRemoteKeys(remoteKeys: List<ArticleRemoteKeys>)

    @Query("DELETE FROM ArticleRemoteKeys")
    suspend fun deleteAllArticleRemoteKeys()
}