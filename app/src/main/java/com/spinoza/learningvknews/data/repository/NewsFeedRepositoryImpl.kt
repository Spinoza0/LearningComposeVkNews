package com.spinoza.learningvknews.data.repository

import android.app.Application
import com.spinoza.learningvknews.data.mapper.toFeedPosts
import com.spinoza.learningvknews.data.mapper.toPostComments
import com.spinoza.learningvknews.data.network.ApiFactory
import com.spinoza.learningvknews.data.network.ApiService
import com.spinoza.learningvknews.data.network.TokenStorageImpl
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.TokenStorage
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.PostComment
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.domain.model.StatisticType
import com.spinoza.learningvknews.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

class NewsFeedRepositoryImpl private constructor(
    application: Application,
    private val tokenStorage: TokenStorage = TokenStorageImpl,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val apiService: ApiService = ApiFactory.apiService,
) : NewsFeedRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)
    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom != null || feedPosts.isEmpty()) {
                val response = if (startFrom == null) {
                    apiService.loadRecommendation(tokenStorage.getToken())
                } else {
                    apiService.loadRecommendation(tokenStorage.getToken(), startFrom)
                }
                nextFrom = response.newsFeedContent.nextFrom
                _feedPosts.addAll(response.toFeedPosts())
            }
            emit(feedPosts)
        }
    }

    override fun isLoggedIn(): Boolean {
        tokenStorage.setToken(token?.accessToken)
        return token?.isValid ?: false
    }

    override val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = repositoryScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost): Unit = withContext(ioDispatcher) {
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
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun deletePost(feedPost: FeedPost): Unit = withContext(ioDispatcher) {
        apiService.ignorePost(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun getComments(feedPost: FeedPost): List<PostComment> =
        withContext(ioDispatcher) {
            val response =
                apiService.getComments(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
            response.content.toPostComments()
        }

    companion object {

        @Volatile
        private var instance: NewsFeedRepositoryImpl? = null
        private val LOCK = Unit

        fun getInstance(application: Application): NewsFeedRepositoryImpl {
            instance?.let { return it }
            synchronized(LOCK) {
                instance?.let { return it }
                return NewsFeedRepositoryImpl(application).also { instance = it }
            }
        }

        fun getInstance(): NewsFeedRepositoryImpl {
            instance?.let { return it }
            throw RuntimeException("NewsFeedRepositoryImpl not created")
        }
    }
}