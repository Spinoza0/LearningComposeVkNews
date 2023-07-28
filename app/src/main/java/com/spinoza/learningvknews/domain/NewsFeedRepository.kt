package com.spinoza.learningvknews.domain

import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.PostComment
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    val recommendations: StateFlow<List<FeedPost>>

    fun isLoggedIn(): Boolean

    suspend fun loadNextData()

    suspend fun changeLikeStatus(feedPost: FeedPost)

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun getComments(feedPost: FeedPost): List<PostComment>
}