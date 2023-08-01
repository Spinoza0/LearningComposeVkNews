package com.spinoza.learningvknews

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.spinoza.learningvknews.di.TEST_COMMENT_TEXT
import com.spinoza.learningvknews.di.TEST_CONTENT_TEXT
import com.spinoza.learningvknews.presentation.feature.main.MainActivity
import org.junit.Rule
import org.junit.Test

class MainScreenTests {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldShowMainScreen(): Unit = with(rule) {
        onNodeWithText(getString(R.string.navigation_item_main)).assertIsDisplayed()
        onNodeWithText(getString(R.string.navigation_item_profile)).assertIsDisplayed()
        onNodeWithText(getString(R.string.navigation_item_favourite)).assertIsDisplayed()
        onNodeWithText(TEST_CONTENT_TEXT).assertIsDisplayed()
    }

    @Test
    fun shouldShowCommentsScreen(): Unit = with(rule) {
        onNode(hasContentDescription(getString(R.string.comments_count))).performClick()

        onNodeWithText(TEST_COMMENT_TEXT).assertIsDisplayed()
    }

    private fun getString(resId: Int) = rule.activity.getString(resId)
}