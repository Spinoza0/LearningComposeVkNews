package com.spinoza.learningvknews.domain

import com.spinoza.learningvknews.domain.model.FeedPost

interface VkApiService {

    suspend fun loadRecommendation(): List<FeedPost>
}