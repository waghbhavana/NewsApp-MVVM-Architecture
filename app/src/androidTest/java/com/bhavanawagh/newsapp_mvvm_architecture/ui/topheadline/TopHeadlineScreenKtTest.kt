package com.bhavanawagh.newsapp_mvvm_architecture.ui.topheadline

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.bhavanawagh.newsapp_mvvm_architecture.R
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.ApiArticle
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.SourceApi
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.UiState
import org.junit.Rule
import org.junit.Test

class TopHeadlineScreenKtTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_whenUiStateIsLoading_isShown() {
        composeTestRule.setContent {
            TopHeadlineScreen(
                uiState = UiState.Loading,
                onNewsClick = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.loading))
            .assertExists()
    }

    @Test
    fun articles_whenUistateIsSuccess_isShown() {
        composeTestRule.setContent {
            TopHeadlineScreen(
                uiState = UiState.Success(testApiArticles),
                onNewsClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                testApiArticles[0].title,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    testApiArticles[5].title,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testApiArticles[5].title,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun error_whenUiStateIsError_isShown() {
        val errorMessage = "Error message"

        composeTestRule.setContent {

            TopHeadlineScreen(uiState = UiState.Error(errorMessage), onNewsClick = {})
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertExists()
    }

}

private val testApiArticles = listOf(
    ApiArticle(
        title = "title1",
        description = "description1",
        url = "url1",
        imageUrl = "imageUrl1",
        sourceApi = SourceApi(id = "sourceId1", name = "sourceName1")
    ),
    ApiArticle(
        title = "title2",
        description = "description2",
        url = "url2",
        imageUrl = "imageUrl2",
        sourceApi = SourceApi(id = "sourceId2", name = "sourceName2")
    ),
    ApiArticle(
        title = "title3",
        description = "description3",
        url = "url3",
        imageUrl = "imageUrl3",
        sourceApi = SourceApi(id = "sourceId3", name = "sourceName3")
    ),
    ApiArticle(
        title = "title4",
        description = "description4",
        url = "url4",
        imageUrl = "imageUrl4",
        sourceApi = SourceApi(id = "sourceId4", name = "sourceName4")
    ),
    ApiArticle(
        title = "title5",
        description = "description5",
        url = "url5",
        imageUrl = "imageUrl5",
        sourceApi = SourceApi(id = "sourceId5", name = "sourceName5")
    ),
    ApiArticle(
        title = "title6",
        description = "description6",
        url = "url6",
        imageUrl = "imageUrl6",
        sourceApi = SourceApi(id = "sourceId6", name = "sourceName6")
    )
)
