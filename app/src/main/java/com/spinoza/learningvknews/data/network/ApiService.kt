package com.spinoza.learningvknews.data.network

import com.spinoza.learningvknews.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendation(@Query("access_token") token: String): NewsFeedResponseDto
}