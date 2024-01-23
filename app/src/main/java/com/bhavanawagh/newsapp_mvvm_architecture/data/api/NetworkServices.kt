package com.bhavanawagh.newsapp_mvvm_architecture.data.api

import com.bhavanawagh.newsapp_mvvm_architecture.data.model.NewsSourceResponse
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.TopHeadlinesResponse
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkServices {

    @Headers("X-api-key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country:String):TopHeadlinesResponse


    @Headers("X-api-key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getNewsSources():NewsSourceResponse

    @Headers("X-api-key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlinesBySource(@Query("sources") sources:String):TopHeadlinesResponse

    @Headers("X-api-key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlinesByLanguage(@Query("language") sources:String):TopHeadlinesResponse


    @Headers("X-api-key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlinesBySearch(@Query("country") country: String,@Query("q") query:String):TopHeadlinesResponse

}