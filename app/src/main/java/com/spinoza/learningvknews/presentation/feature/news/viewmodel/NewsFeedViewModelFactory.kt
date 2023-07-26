package com.spinoza.learningvknews.presentation.feature.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.NewsFeedRepository

class NewsFeedViewModelFactory(
    private val repository: NewsFeedRepository,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(repository) as T
    }
}