package com.spinoza.learningvknews.presentation.feature.comments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.model.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost,
    private val repository: NewsFeedRepository,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost, repository) as T
    }
}