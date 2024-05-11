package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {


    fun getTopHeadlines(country:String): Flow<List<ApiArticle>>{
        return flow{
            emit(networkService.getTopHeadlines(country))
        }.map{
            it.apiArticles
        }
    }
}