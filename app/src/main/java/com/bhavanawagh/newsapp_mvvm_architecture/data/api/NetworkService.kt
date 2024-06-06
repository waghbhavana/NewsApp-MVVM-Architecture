package com.bhavanawagh.newsapp_mvvm_architecture.data.api

import com.bhavanawagh.newsapp_mvvm_architecture.data.model.NewsSourceResponse
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {


    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadlinesResponse

    @GET("top-headlines")
    suspend fun getTopHeadlinesWithPagination(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): TopHeadlinesResponse


    @GET("top-headlines/sources")
    suspend fun getNewsSources(): NewsSourceResponse


    @GET("top-headlines")
    suspend fun getTopHeadlinesBySource(
        @Query("sources") sources: String, @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): TopHeadlinesResponse


    @GET("top-headlines")
    suspend fun getTopHeadlinesByLanguage(
        @Query("language") sources: String, @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): TopHeadlinesResponse


    @GET("everything")
    suspend fun getTopHeadlinesBySearch(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): TopHeadlinesResponse

}