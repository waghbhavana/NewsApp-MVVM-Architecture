package com.bhavanawagh.newsapp_mvvm_architecture.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.bhavanawagh.newsapp_mvvm_architecture.data.api.NetworkService
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.TopHeadlinesResponse
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY_LIST
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.EXTRAS_COUNTRY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineRepositoryTest {


    @Mock
    private lateinit var  networkService : NetworkService

    private lateinit var topHeadlineRepository: TopHeadlineRepository



    @Before
    fun setUp() {
        topHeadlineRepository = TopHeadlineRepository(networkService)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getTopHeadlines_whenRepositoryResponseSuccess_setToUiStateSuccess() {
        runTest {
            val article = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "imageUrl",
                sourceApi = SourceApi(id = "1", name = "source name")
            )
            val articleList = listOf(article)

            val topHeadlinesResponse = TopHeadlinesResponse("success", 1, articleList)
            Mockito.doReturn(topHeadlinesResponse).`when`(networkService).getTopHeadlines(EXTRAS_COUNTRY)
            topHeadlineRepository.getTopHeadlines(EXTRAS_COUNTRY).test {
                assertEquals(topHeadlinesResponse.apiArticles,awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
                 Mockito.verify(networkService,Mockito.times(1)).getTopHeadlines(EXTRAS_COUNTRY)


        }
    }

    @Test
    fun getTopHeadlines_whenRepositoryResponseError_setToUiStateError(){
        runTest {

            val errorMessage = "Error Message"

            Mockito.doThrow(RuntimeException(errorMessage)).`when`(networkService).getTopHeadlines(EXTRAS_COUNTRY)

            topHeadlineRepository.getTopHeadlines(EXTRAS_COUNTRY).test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            Mockito.verify(networkService,Mockito.times(1)).getTopHeadlines(EXTRAS_COUNTRY)
        }
    }
}