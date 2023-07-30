package com.spinoza.learningvknews.domain

import com.spinoza.learningvknews.domain.model.AuthState
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.PostComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    val recommendations: StateFlow<List<FeedPost>>

    val authState: StateFlow<AuthState>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun changeLikeStatus(feedPost: FeedPost)

    suspend fun deletePost(feedPost: FeedPost)

    fun getComments(feedPost: FeedPost): Flow<List<PostComment>>
}