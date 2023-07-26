package com.spinoza.learningvknews.presentation.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.NewsFeedRepository

class MainViewModelFactory(
    private val repository: NewsFeedRepository,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}