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
    private val _feedPosts = mutableListOf<FeedPost>()
    private var nextFrom: String? = null

    override fun isLoggedIn(): Boolean {
        tokenStorage.setToken(token?.accessToken)
        return token?.isValid ?: false
    }

    override fun getFeedPosts(): List<FeedPost> = _feedPosts.toList()

    override suspend fun loadRecommendation(): List<FeedPost> = withContext(ioDispatcher) {
        val startFrom = nextFrom
        if (startFrom != null || _feedPosts.isEmpty()) {
            val response = if (startFrom == null) {
                apiService.loadRecommendation(tokenStorage.getToken())
            } else {
                apiService.loadRecommendation(tokenStorage.getToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            _feedPosts.addAll(response.toFeedPosts())
        }
        getFeedPosts()
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost): List<FeedPost> =
        withContext(ioDispatcher) {
            val response = if (feedPost.isLiked) {
                apiService.deleteLike(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
            } else {
                apiService.addLike(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
            }
            val newLikesCount = response.likes.count
            val newStatistics = feedPost.statistics.toMutableList().apply {
                removeIf { it.type == StatisticType.LIKES }
                add(StatisticItem(StatisticType.LIKES, newLikesCount))
            }
            val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
            val postIndex = _feedPosts.indexOf(feedPost)
            _feedPosts[postIndex] = newPost
            getFeedPosts()
        }

    override suspend fun deletePost(feedPost: FeedPost): Unit = withContext(ioDispatcher) {
        apiService.ignorePost(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
        _feedPosts.remove(feedPost)
    }
}