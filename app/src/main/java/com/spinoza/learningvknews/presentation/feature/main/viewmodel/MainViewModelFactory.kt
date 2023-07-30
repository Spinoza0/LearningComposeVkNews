package com.spinoza.learningvknews.presentation.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.usecase.CheckAuthStateUseCase
import com.spinoza.learningvknews.domain.usecase.GetAuthStateUseCase

class MainViewModelFactory(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getAuthStateUseCase, checkAuthStateUseCase) as T
    }
}