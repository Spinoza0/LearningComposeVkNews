package com.spinoza.learningvknews.presentation.feature.mainscreen.model

sealed class AuthState {

    object Initial : AuthState()

    object Authorized : AuthState()

    object NotAuthorized : AuthState()
}
