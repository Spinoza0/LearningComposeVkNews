package com.spinoza.learningvknews.presentation.feature.news.model

import com.spinoza.learningvknews.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()
}