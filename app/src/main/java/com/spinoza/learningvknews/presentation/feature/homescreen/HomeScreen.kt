package com.spinoza.learningvknews.presentation.feature.homescreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.spinoza.learningvknews.presentation.feature.homescreen.model.HomeScreenState
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
) {
    when (val screenState =
        viewModel.screenState.observeAsState(HomeScreenState.Initial).value
    ) {
        is HomeScreenState.Posts -> PostsScreen(screenState.posts, viewModel, paddingValues)
        is HomeScreenState.Comments -> {
            CommentsScreen(screenState.feedPost, screenState.comments) { viewModel.closeComments() }
            BackHandler { viewModel.closeComments() }
        }

        is HomeScreenState.Initial -> {}
    }
}