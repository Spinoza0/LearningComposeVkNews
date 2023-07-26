package com.spinoza.learningvknews.data.repository

import com.spinoza.learningvknews.data.mapper.toFeedPosts
import com.spinoza.learningvknews.data.network.ApiFactory
import com.spinoza.learningvknews.data.network.TokenStorageImpl
import com.spinoza.learningvknews.domain.VkApiService
import com.spinoza.learningvknews.domain.model.FeedPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object VkApiServiceImpl : VkApiService {

    private val ioDispatcher = Dispatchers.IO
    private val tokenStorage = TokenStorageImpl
    private val apiService = ApiFactory.apiService

    override suspend fun loadRecommendation(): List<FeedPost> = withContext(ioDispatcher) {
        apiService.loadRecommendation(tokenStorage.getToken()).toFeedPosts()
    }
}