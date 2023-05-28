package com.spinoza.learningvknews.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.presentation.feature.ActivityResultTest
import com.spinoza.learningvknews.presentation.feature.MainScreen
import com.spinoza.learningvknews.presentation.ui.theme.LearningVkNewsTheme
import com.spinoza.learningvknews.presentation.util.SIZE_SMALL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LearningVkNewsTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                        .padding(SIZE_SMALL.dp)
                ) {
                    // MainScreen()
                    ActivityResultTest()
                }
            }
        }
    }
}