package com.spinoza.learningvknews.presentation.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.learningvknews.domain.usecase.CheckAuthStateUseCase
import com.spinoza.learningvknews.domain.usecase.GetAuthStateUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
) : ViewModel() {

    val authState = getAuthStateUseCase()

    fun preformAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}