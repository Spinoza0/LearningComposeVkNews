package com.spinoza.learningvknews.domain.usecase

import com.spinoza.learningvknews.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(private val repository: NewsFeedRepository) {

    suspend operator fun invoke() = repository.checkAuthState()
}