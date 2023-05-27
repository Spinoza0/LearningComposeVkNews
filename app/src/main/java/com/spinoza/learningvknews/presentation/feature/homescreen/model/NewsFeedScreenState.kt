package com.spinoza.learningvknews.presentation.feature.homescreen.model

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()
}