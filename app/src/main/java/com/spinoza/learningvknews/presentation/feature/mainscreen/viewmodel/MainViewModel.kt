package com.spinoza.learningvknews.presentation.feature.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.presentation.feature.mainscreen.model.AuthState
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)

    private val authState: StateFlow<AuthState>
        get() = _authState.asStateFlow()

    fun preformAuthResult(result: VKAuthenticationResult) {
        when (result) {
                        is VKAuthenticationResult.Success -> {}
                        is VKAuthenticationResult.Failed -> {}
                    }
    }
}