package com.spinoza.learningvknews.presentation.feature.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.presentation.feature.mainscreen.model.AuthState
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)

    val authState: StateFlow<AuthState>
        get() = _authState.asStateFlow()

    init {
        _authState.value = if (VK.isLoggedIn()) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun preformAuthResult(result: VKAuthenticationResult) {
        when (result) {
            is VKAuthenticationResult.Success -> _authState.value = AuthState.Authorized
            is VKAuthenticationResult.Failed -> _authState.value = AuthState.NotAuthorized
        }
    }
}