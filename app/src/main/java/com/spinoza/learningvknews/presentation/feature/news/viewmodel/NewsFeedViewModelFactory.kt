package com.spinoza.learningvknews.presentation.feature.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.VkApiService

class NewsFeedViewModelFactory(
    private val apiService: VkApiService,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(apiService) as T
    }
}