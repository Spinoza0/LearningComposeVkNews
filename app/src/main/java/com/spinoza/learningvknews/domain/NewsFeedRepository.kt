package com.spinoza.learningvknews.domain

import com.spinoza.learningvknews.domain.model.FeedPost

interface NewsFeedRepository {

    fun isLoggedIn(): Boolean

    fun getFeedPosts(): List<FeedPost>

    suspend fun loadRecommendation(): List<FeedPost>

    suspend fun changeLikeStatus(feedPost: FeedPost): List<FeedPost>

    suspend fun deletePost(feedPost: FeedPost)
}