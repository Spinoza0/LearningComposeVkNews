package com.spinoza.learningvknews.presentation.feature.homescreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spinoza.learningvknews.presentation.feature.homescreen.model.FeedPost
import com.spinoza.learningvknews.presentation.feature.homescreen.model.NewsFeedScreenState
import com.spinoza.learningvknews.presentation.feature.homescreen.viewmodel.NewsFeedViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    val viewModel: NewsFeedViewModel = viewModel()
    when (val screenState =
        viewModel.screenState.observeAsState(NewsFeedScreenState.Initial).value
    ) {
        is NewsFeedScreenState.Posts ->
            NewsFeedScreen(screenState.posts, viewModel, paddingValues, onCommentsClickListener)

        is NewsFeedScreenState.Initial -> {}
    }
}