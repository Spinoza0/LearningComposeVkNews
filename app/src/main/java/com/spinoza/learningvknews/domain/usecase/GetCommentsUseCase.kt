package com.spinoza.learningvknews.domain.usecase

import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.PostComment
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetCommentsUseCase(private val repository: NewsFeedRepository) {

    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> =
        repository.getComments(feedPost)
}