package com.spinoza.learningvknews.presentation.feature.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.learningvknews.domain.NewsFeedRepository
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.presentation.feature.news.model.NewsFeedScreenState
import kotlinx.coroutines.launch

class NewsFeedViewModel(private val repository: NewsFeedRepository) : ViewModel() {

    val screenState: LiveData<NewsFeedScreenState>
        get() = _screenState

    private val _screenState = MutableLiveData<NewsFeedScreenState>(NewsFeedScreenState.Initial)

    init {
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

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == statisticItem.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = mutableListOf<FeedPost>()
        currentState.posts.forEach { oldFeedPost ->
            if (oldFeedPost.id == feedPost.id) {
                newPosts.add(newFeedPost)
            } else {
                newPosts.add(oldFeedPost)
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(newPosts)
    }

    fun delete(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.deletePost(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(repository.getFeedPosts())
        }
    }
}