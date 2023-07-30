package com.spinoza.learningvknews.domain.usecase

import com.spinoza.learningvknews.domain.model.AuthState
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(private val repository: NewsFeedRepository) {

    operator fun invoke(): StateFlow<AuthState> = repository.getAuthState()
}