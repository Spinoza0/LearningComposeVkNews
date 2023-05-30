package com.spinoza.learningvknews.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.spinoza.learningvknews.presentation.feature.loginscreen.LoginScreen
import com.spinoza.learningvknews.presentation.ui.theme.LearningVkNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LearningVkNewsTheme {
//                val launcher = rememberLauncherForActivityResult(
//                    contract = VK.getVKAuthActivityResultContract()
//                ) {
//                    when (it) {
//                        is VKAuthenticationResult.Success -> {}
//                        is VKAuthenticationResult.Failed -> {}
//                    }
//                }
//                SideEffect {
//                    launcher.launch(arrayListOf(VKScope.WALL))
//                }
//                MainScreen()
                LoginScreen {

                }
            }
        }
    }
}