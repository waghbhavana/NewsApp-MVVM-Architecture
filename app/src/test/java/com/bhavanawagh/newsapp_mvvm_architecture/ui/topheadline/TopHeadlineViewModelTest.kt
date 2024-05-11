package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import app.cash.turbine.test
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import com.bhavanawagh.newsapp_mvvm_architecture.utils.AppConstants.EXTRAS_COUNTRY
import com.bhavanawagh.newsapp_mvvm_architecture.utils.DefaultDispatcherProviderTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.testTimeSource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {


    @Mock
    lateinit var topHeadlineRepository: TopHeadlineRepository

    private lateinit var defaultDispatcher:  DefaultDispatcherProviderTest

    private lateinit var topHeadlineViewModel: TopHeadlineViewModel


    @Before
    fun setUp() {
        defaultDispatcher= DefaultDispatcherProviderTest()
    }


    @Test
    fun fetchTopHeadLines_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {

            Mockito.doReturn(flowOf(emptyList<ApiArticle>())).`when`(topHeadlineRepository)
                .getTopHeadlines(EXTRAS_COUNTRY)

            topHeadlineViewModel= TopHeadlineViewModel(topHeadlineRepository,defaultDispatcher)
            topHeadlineViewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<ApiArticle>>()),awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            Mockito.verify(topHeadlineRepository, Mockito.times(1)).getTopHeadlines(EXTRAS_COUNTRY)

        }
    }

    @Test
    fun fetchTopHeadLines_whenRepositoryResponseError_shouldSetErrorUiState(){
        runTest {
            val errorMessage="error"
            Mockito.doReturn(flow<List<ApiArticle>> {
                throw Error(errorMessage)  //UiState.Error(IllegalStateException(errorMessage).toString())
            }).`when`(topHeadlineRepository).getTopHeadlines(EXTRAS_COUNTRY)

            topHeadlineViewModel= TopHeadlineViewModel(topHeadlineRepository,defaultDispatcher)
            topHeadlineViewModel.uiState.test {
//                assertEquals(
//                    UiState.Error(IllegalStateException(errorMessage).toString()),
//                    awaitItem()
//                )
                assertEquals(
                    UiState.Error(errorMessage),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }

            Mockito.verify(topHeadlineRepository,Mockito.times(1)).getTopHeadlines(EXTRAS_COUNTRY)
        }
    }
}