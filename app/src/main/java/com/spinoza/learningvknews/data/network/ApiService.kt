package com.spinoza.learningvknews.data.network

import com.spinoza.learningvknews.data.model.LikesCountResponseDto
import com.spinoza.learningvknews.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=$VK_API_VERSION")
    suspend fun loadRecommendation(@Query(QUERY_TOKEN) token: String): NewsFeedResponseDto

    @GET("likes.add?v=$VK_API_VERSION&type=post")
    suspend fun addLike(
        @Query(QUERY_TOKEN) token: String,
        @Query(QUERY_OWNER_ID) ownerId: Long,
        @Query(QUERY_POST_ID) postId: Long,
    ): LikesCountResponseDto

    private companion object {
        const val VK_API_VERSION = "5.131"
        const val QUERY_TOKEN = "access_token"
        const val QUERY_OWNER_ID = "owner_id"
        const val QUERY_POST_ID = "item_id"
    }
}