package com.spinoza.learningvknews.presentation.feature.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.presentation.feature.news.model.NewsFeedScreenState
import com.spinoza.learningvknews.presentation.feature.news.viewmodel.NewsFeedViewModel
import com.spinoza.learningvknews.presentation.feature.news.NewsFeedScreen

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