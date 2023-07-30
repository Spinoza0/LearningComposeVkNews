package com.spinoza.learningvknews.domain.usecase

import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetRecommendationsUseCase(private val repository: NewsFeedRepository) {

    operator fun invoke(): StateFlow<List<FeedPost>> = repository.getRecommendations()
}