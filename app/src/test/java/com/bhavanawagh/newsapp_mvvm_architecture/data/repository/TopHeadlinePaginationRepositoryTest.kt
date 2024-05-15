package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlinePaginationRepositoryTest {


    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var  topHeadlinePaginationRepository: TopHeadlinePaginationRepository

    @Before
    fun setUp() {
       topHeadlinePaginationRepository = TopHeadlinePaginationRepository(networkService)
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun getTopHeadlinesTest(){
        runTest {
            val mockApiResponse = mockedApiArticles()
        }
    }



    private fun mockedApiArticles(): List<ApiArticle> {
        val source = SourceApi(id = "sourceId", name = "sourceName")
        val article = ApiArticle(
            title = "title",
            description = "description",
            url = "url",
            imageUrl = "urlToImage",
            sourceApi = source
        )
        return listOf(
            article
        )
    }
}