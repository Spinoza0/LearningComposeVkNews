package com.spinoza.learningvknews.presentation.feature.homescreen.model

import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.PostComment

sealed class HomeScreenState {

    object Initial : HomeScreenState()

    class Posts(val posts: List<FeedPost>) : HomeScreenState()

    class Comments(val feedPost: FeedPost, val comments: List<PostComment>) : HomeScreenState()
}