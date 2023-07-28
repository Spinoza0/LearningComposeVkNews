package com.spinoza.learningvknews.data.network

import com.spinoza.learningvknews.data.model.LikesCountResponseDto
import com.spinoza.learningvknews.data.model.NewsFeedResponseDto
import com.spinoza.learningvknews.data.model.PostCommentsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=$VK_API_VERSION")
    suspend fun loadRecommendation(@Query(QUERY_TOKEN) token: String): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=$VK_API_VERSION")
    suspend fun loadRecommendation(
        @Query(QUERY_TOKEN) token: String,
        @Query(QUERY_START_FROM) startFrom: String,
    ): NewsFeedResponseDto

    @GET("likes.add?v=$VK_API_VERSION&type=post")
    suspend fun addLike(
        @Query(QUERY_TOKEN) token: String,
        @Query(QUERY_OWNER_ID) ownerId: Long,
        @Query(QUERY_ITEM_ID) postId: Long,
    ): LikesCountResponseDto

    @GET("likes.delete?v=$VK_API_VERSION&type=post")
    suspend fun deleteLike(
        @Query(QUERY_TOKEN) token: String,
        @Query(QUERY_OWNER_ID) ownerId: Long,
        @Query(QUERY_ITEM_ID) postId: Long,
    ): LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=$VK_API_VERSION&type=wall")
    suspend fun ignorePost(
        @Query(QUERY_TOKEN) token: String,
        @Query(QUERY_OWNER_ID) ownerId: Long,
        @Query(QUERY_ITEM_ID) postId: Long,
    )

    @GET("wall.getComments?v=$VK_API_VERSION&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query(QUERY_TOKEN) token: String,
        @Query(QUERY_OWNER_ID) ownerId: Long,
        @Query(QUERY_POST_ID) postId: Long,
    ): PostCommentsResponseDto

    private companion object {
        const val VK_API_VERSION = "5.131"
        const val QUERY_TOKEN = "access_token"
        const val QUERY_OWNER_ID = "owner_id"
        const val QUERY_ITEM_ID = "item_id"
        const val QUERY_POST_ID = "post_id"
        const val QUERY_START_FROM = "start_from"
    }
}