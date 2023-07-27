package com.spinoza.learningvknews.presentation.feature.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.presentation.feature.news.model.NewsFeedScreenState
import kotlinx.coroutines.launch

class NewsFeedViewModel(private val repository: NewsFeedRepository) : ViewModel() {

    val screenState: LiveData<NewsFeedScreenState>
        get() = _screenState

    private val _screenState = MutableLiveData<NewsFeedScreenState>(NewsFeedScreenState.Initial)

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendation()
    }

    private fun loadRecommendation() {
        viewModelScope.launch {
            _screenState.value = NewsFeedScreenState.Posts(repository.loadRecommendation())
        }
    }

    fun loadNextRecommendations() {
        _screenState.value = NewsFeedScreenState.Posts(repository.getFeedPosts(), true)
        loadRecommendation()
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            _screenState.value = NewsFeedScreenState.Posts(repository.changeLikeStatus(feedPost))
        }
    }

    fun delete(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.deletePost(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(repository.getFeedPosts())
        }
    }
}