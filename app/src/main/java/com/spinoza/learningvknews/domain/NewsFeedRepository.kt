package com.spinoza.learningvknews.domain

import com.spinoza.learningvknews.domain.model.FeedPost

interface NewsFeedRepository {

    fun isLoggedIn(): Boolean

    suspend fun loadRecommendation(): List<FeedPost>

    suspend fun addLike(feedPost: FeedPost): List<FeedPost>
}