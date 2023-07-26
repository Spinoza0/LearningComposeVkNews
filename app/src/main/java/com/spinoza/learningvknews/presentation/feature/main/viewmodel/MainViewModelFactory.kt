package com.spinoza.learningvknews.presentation.feature.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.TokenStorage

class MainViewModelFactory(
    private val application: Application,
    private val tokenStorage: TokenStorage,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application, tokenStorage) as T
    }
}