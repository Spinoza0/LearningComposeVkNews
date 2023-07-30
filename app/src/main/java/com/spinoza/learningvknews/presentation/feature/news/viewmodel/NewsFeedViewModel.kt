package com.spinoza.learningvknews.presentation.feature.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.usecase.ChangeLikeStatusUseCase
import com.spinoza.learningvknews.domain.usecase.DeletePostUseCase
import com.spinoza.learningvknews.domain.usecase.GetRecommendationsUseCase
import com.spinoza.learningvknews.domain.usecase.LoadNextDataUseCase
import com.spinoza.learningvknews.extensions.mergeWith
import com.spinoza.learningvknews.presentation.feature.news.model.NewsFeedScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(
    getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    private val loadNextDataEvents = MutableSharedFlow<Unit>()
    private val recommendations = getRecommendationsUseCase()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        // TODO
    }

    private val loadNextDataFlow = flow {
        loadNextDataEvents.collect {
            emit(
                NewsFeedScreenState.Posts(
                    posts = recommendations.value,
                    iaNextDataLoading = true
                )
            )
        }
    }

    val screenState = recommendations
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataEvents.emit(Unit)
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun delete(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }
    }
}