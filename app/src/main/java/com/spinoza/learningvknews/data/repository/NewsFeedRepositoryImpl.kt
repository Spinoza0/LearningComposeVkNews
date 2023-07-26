package com.spinoza.learningvknews.data.repository

import android.app.Application
import com.spinoza.learningvknews.data.mapper.toFeedPosts
import com.spinoza.learningvknews.data.network.ApiFactory
import com.spinoza.learningvknews.data.network.ApiService
import com.spinoza.learningvknews.data.network.TokenStorageImpl
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.TokenStorage
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.domain.model.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsFeedRepositoryImpl(
    application: Application,
    private val tokenStorage: TokenStorage = TokenStorageImpl,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val apiService: ApiService = ApiFactory.apiService,
) : NewsFeedRepository {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)
    private val feedPosts = mutableListOf<FeedPost>()

    override fun isLoggedIn(): Boolean {
        tokenStorage.setToken(token?.accessToken)
        return token?.isValid ?: false
    }

    override suspend fun loadRecommendation(): List<FeedPost> = withContext(ioDispatcher) {
        val posts = apiService.loadRecommendation(tokenStorage.getToken()).toFeedPosts()
        feedPosts.addAll(posts)
        feedPosts.toList()
    }

    override suspend fun addLike(feedPost: FeedPost): List<FeedPost> = withContext(ioDispatcher) {
        val response =
            apiService.addLike(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = true)
        val postIndex = feedPosts.indexOf(feedPost)
        feedPosts[postIndex] = newPost
        feedPosts.toList()
    }
}