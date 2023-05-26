package com.spinoza.learningvknews.presentation.feature.homescreen.model

import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    class Comments(val feedPost: FeedPost, val comments: List<PostComment>) : CommentsScreenState()
}