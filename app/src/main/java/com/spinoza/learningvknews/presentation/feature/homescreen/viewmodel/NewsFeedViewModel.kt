package com.spinoza.learningvknews.presentation.feature.homescreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.presentation.feature.homescreen.model.FeedPost
import com.spinoza.learningvknews.presentation.feature.homescreen.model.StatisticItem
import com.spinoza.learningvknews.presentation.feature.homescreen.model.NewsFeedScreenState

class NewsFeedViewModel : ViewModel() {

    val screenState: LiveData<NewsFeedScreenState>
        get() = _screenState

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }

    private val initialState = NewsFeedScreenState.Posts(initialList)
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)

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
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val newPosts = currentState.posts.filter { it.id != feedPost.id }
        _screenState.value = NewsFeedScreenState.Posts(newPosts)
    }
}