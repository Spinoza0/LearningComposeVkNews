package com.spinoza.learningvknews.presentation.feature.comments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.usecase.GetCommentsUseCase

class CommentsViewModelFactory(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost, getCommentsUseCase) as T
    }
}