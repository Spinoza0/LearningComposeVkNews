package com.spinoza.learningvknews

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.spinoza.learningvknews.presentation.feature.main.MainActivity
import org.junit.Rule
import org.junit.Test

class MainScreenTests {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldShowMainScreen(): Unit = with(rule) {
        onNodeWithText(activity.getString(R.string.navigation_item_main)).assertIsDisplayed()
        onNodeWithText(activity.getString(R.string.navigation_item_profile)).assertIsDisplayed()
        onNodeWithText(activity.getString(R.string.navigation_item_favourite)).assertIsDisplayed()
    }
}