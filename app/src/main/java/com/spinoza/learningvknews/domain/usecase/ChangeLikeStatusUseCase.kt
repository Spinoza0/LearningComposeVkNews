package com.spinoza.learningvknews.domain.usecase

import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository

class ChangeLikeStatusUseCase(private val repository: NewsFeedRepository) {

    suspend operator fun invoke(feedPost: FeedPost) =
        repository.changeLikeStatus(feedPost)
}