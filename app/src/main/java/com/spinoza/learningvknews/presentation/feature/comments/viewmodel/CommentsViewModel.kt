package com.spinoza.learningvknews.presentation.feature.comments.viewmodel

import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.usecase.GetCommentsUseCase
import com.spinoza.learningvknews.presentation.feature.comments.CommentsScreenState
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    feedPost: FeedPost,
    getCommentsUseCase: GetCommentsUseCase,
) : ViewModel() {

    val screenState = getCommentsUseCase(feedPost)
        .map { CommentsScreenState.Comments(it) as CommentsScreenState }
}