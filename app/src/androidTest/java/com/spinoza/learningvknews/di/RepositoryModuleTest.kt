package com.spinoza.learningvknews.di

import com.spinoza.learningvknews.domain.model.AuthState
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.PostComment
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.domain.model.StatisticType
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.dsl.module

val repositoryModuleTest = module {

    single<NewsFeedRepository> {

        object : NewsFeedRepository {
            override fun getAuthState(): StateFlow<AuthState> =
                MutableStateFlow<AuthState>(AuthState.Authorized).asStateFlow()

            override fun getRecommendations(): StateFlow<List<FeedPost>> =
                MutableStateFlow(recommendations).asStateFlow()

            override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> =
                MutableStateFlow(comments).asStateFlow()

            override suspend fun checkAuthState() {}

            override suspend fun loadNextData() {}

            override suspend fun changeLikeStatus(feedPost: FeedPost) {}

            override suspend fun deletePost(feedPost: FeedPost) {}
        }
    }
}

private val statistics = listOf(
    StatisticItem(StatisticType.SHARES, 1),
    StatisticItem(StatisticType.COMMENTS, 1),
    StatisticItem(StatisticType.LIKES, 1),
    StatisticItem(StatisticType.VIEWS, 1),
)

const val TEST_CONTENT_TEXT = "content"
const val TEST_COMMENT_TEXT = "comment"

private val recommendations = listOf(
    FeedPost(
        1,
        1,
        "Test community",
        "01.01.2023",
        "",
        TEST_CONTENT_TEXT,
        "",
        statistics,
        false
    )
)

private val comments = listOf(
    PostComment(
        1,
        "Author",
        "",
        TEST_COMMENT_TEXT,
        "01.01.2023"
    )
)