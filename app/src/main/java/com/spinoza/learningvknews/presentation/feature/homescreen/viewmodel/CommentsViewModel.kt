package com.spinoza.learningvknews.presentation.feature.homescreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.PostComment
import com.spinoza.learningvknews.presentation.feature.homescreen.model.CommentsScreenState

class CommentsViewModel : ViewModel() {

    val screenState: LiveData<CommentsScreenState>
        get() = _screenState

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)

    fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(10) {
                add(PostComment(id = it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost, comments)
    }
}