package com.spinoza.learningvknews.data.repository

import android.app.Application
import com.spinoza.learningvknews.data.mapper.toFeedPosts
import com.spinoza.learningvknews.data.mapper.toPostComments
import com.spinoza.learningvknews.data.network.ApiFactory
import com.spinoza.learningvknews.data.network.ApiService
import com.spinoza.learningvknews.data.network.TokenStorageImpl
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.TokenStorage
import com.spinoza.learningvknews.domain.model.AuthState
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.PostComment
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.domain.model.StatisticType
import com.spinoza.learningvknews.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepositoryImpl private constructor(
    application: Application,
    private val tokenStorage: TokenStorage = TokenStorageImpl,
    private val apiService: ApiService = ApiFactory.apiService,
) : NewsFeedRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = REPLAY_VALUE)
    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = REPLAY_VALUE)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val loadedListFlow = flow {
        loadNextData()
        nextDataNeededEvents.collect {
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
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    override val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = repositoryScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override val authState: StateFlow<AuthState> = flow {
        checkAuthState()
        checkAuthStateEvents.collect {
            val currentToken = token
            var accessToken: String? = null
            var state: AuthState = AuthState.NotAuthorized
            if (currentToken != null && currentToken.isValid) {
                state = AuthState.Authorized
                accessToken = currentToken.accessToken
            }
            tokenStorage.setToken(accessToken)
            emit(state)
        }
    }.stateIn(
        scope = repositoryScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
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

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override fun getComments(feedPost: FeedPost): Flow<List<PostComment>> = flow {
        val response =
            apiService.getComments(tokenStorage.getToken(), feedPost.communityId, feedPost.id)
        emit(response.content.toPostComments())
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 3000L
        private const val REPLAY_VALUE = 1

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