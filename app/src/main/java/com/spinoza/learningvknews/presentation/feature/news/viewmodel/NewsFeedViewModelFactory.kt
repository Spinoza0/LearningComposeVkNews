package com.spinoza.learningvknews.presentation.feature.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.learningvknews.domain.usecase.ChangeLikeStatusUseCase
import com.spinoza.learningvknews.domain.usecase.DeletePostUseCase
import com.spinoza.learningvknews.domain.usecase.GetRecommendationsUseCase
import com.spinoza.learningvknews.domain.usecase.LoadNextDataUseCase

class NewsFeedViewModelFactory(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(
            getRecommendationsUseCase,
            loadNextDataUseCase,
            changeLikeStatusUseCase,
            deletePostUseCase
        ) as T
    }
}