package com.spinoza.learningvknews.presentation.feature.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.domain.TokenStorage
import com.spinoza.learningvknews.presentation.feature.main.model.AuthState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(application: Application, tokenStorage: TokenStorage) :
    ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)

    val authState: StateFlow<AuthState>
        get() = _authState.asStateFlow()

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        var isLoggedIn = false
        if (token != null) {
            isLoggedIn = token.isValid
            tokenStorage.setToken(token.accessToken)
        }
        _authState.value = if (isLoggedIn) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun preformAuthResult(result: VKAuthenticationResult) {
        when (result) {
            is VKAuthenticationResult.Success -> _authState.value = AuthState.Authorized
            is VKAuthenticationResult.Failed -> _authState.value = AuthState.NotAuthorized
        }
    }
}