package com.spinoza.learningvknews.presentation.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spinoza.learningvknews.data.repository.NewsFeedRepositoryImpl
import com.spinoza.learningvknews.presentation.feature.main.model.AuthState
import com.spinoza.learningvknews.presentation.feature.main.viewmodel.MainViewModel
import com.spinoza.learningvknews.presentation.feature.main.viewmodel.MainViewModelFactory
import com.spinoza.learningvknews.presentation.theme.LearningVkNewsTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LearningVkNewsTheme {
                val viewModel: MainViewModel =
                    viewModel(factory = MainViewModelFactory(NewsFeedRepositoryImpl(application)))
                val authState = viewModel.authState.collectAsState()
                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.preformAuthResult(it)
                }

                when (authState.value) {
                    is AuthState.Authorized -> MainScreen(application)
                    is AuthState.NotAuthorized -> LoginScreen {
                        launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }

                    is AuthState.Initial -> {}
                }
            }
        }
    }
}