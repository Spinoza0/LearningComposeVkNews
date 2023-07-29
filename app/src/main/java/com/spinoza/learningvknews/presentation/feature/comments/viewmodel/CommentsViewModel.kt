package com.spinoza.learningvknews.presentation.feature.comments.viewmodel

import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.presentation.feature.comments.CommentsScreenState
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    feedPost: FeedPost,
    repository: NewsFeedRepository,
) : ViewModel() {

    val screenState = repository.getComments(feedPost)
        .map { CommentsScreenState.Comments(it) as CommentsScreenState }
}