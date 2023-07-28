package com.spinoza.learningvknews.presentation.feature.comments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.presentation.feature.comments.CommentsScreenState
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val feedPost: FeedPost,
    private val repository: NewsFeedRepository,
) : ViewModel() {

    val screenState: LiveData<CommentsScreenState>
        get() = _screenState

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)

    init {
        loadComments()
    }

    private fun loadComments() {
        viewModelScope.launch {
            val comments = repository.getComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(comments)
        }
    }
}