package com.spinoza.learningvknews.domain.usecase

import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository

class DeletePostUseCase(private val repository: NewsFeedRepository) {

    suspend operator fun invoke(feedPost: FeedPost) =
        repository.deletePost(feedPost)
}