package com.spinoza.learningvknews.presentation.feature.homescreen.model

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    class Comments(val feedPost: FeedPost, val comments: List<PostComment>) : CommentsScreenState()
}