package com.spinoza.learningvknews.presentation.feature.news

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spinoza.learningvknews.data.repository.NewsFeedRepositoryImpl
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.presentation.feature.news.model.NewsFeedScreenState
import com.spinoza.learningvknews.presentation.feature.news.viewmodel.NewsFeedViewModel
import com.spinoza.learningvknews.presentation.feature.news.viewmodel.NewsFeedViewModelFactory
import com.spinoza.learningvknews.presentation.theme.DarkBlue

@Composable
fun NewsFeedScreen(
    application: Application,
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    val viewModel: NewsFeedViewModel =
        viewModel(factory = NewsFeedViewModelFactory(NewsFeedRepositoryImpl(application)))
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)
    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> FeedPosts(
            currentState.posts,
            viewModel,
            paddingValues,
            currentState.iaNextDataLoading,
            onCommentsClickListener
        )

        is NewsFeedScreenState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = DarkBlue)
        }

        is NewsFeedScreenState.Initial -> {}
    }
}