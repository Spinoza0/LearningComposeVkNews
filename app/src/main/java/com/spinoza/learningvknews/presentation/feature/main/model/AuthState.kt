package com.spinoza.learningvknews.presentation.feature.main.model

sealed class AuthState {

    object Initial : AuthState()

    object Authorized : AuthState()

    object NotAuthorized : AuthState()
}
