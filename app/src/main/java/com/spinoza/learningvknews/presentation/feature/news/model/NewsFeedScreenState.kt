package com.spinoza.learningvknews.presentation.feature.news.model

import com.spinoza.learningvknews.domain.model.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    object Loading : NewsFeedScreenState()

    class Posts(val posts: List<FeedPost>, val iaNextDataLoading: Boolean = false) :
        NewsFeedScreenState()
}