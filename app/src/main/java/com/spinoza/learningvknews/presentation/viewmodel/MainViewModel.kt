package com.spinoza.learningvknews.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.PostComment
import com.spinoza.learningvknews.domain.StatisticItem
import com.spinoza.learningvknews.presentation.feature.homescreen.model.HomeScreenState

class MainViewModel : ViewModel() {

    val screenState: LiveData<HomeScreenState>
        get() = _screenState

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }

    private val comments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(PostComment(id = it))
        }
    }

    private val initialState = HomeScreenState.Posts(initialList)
    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    private var savedState: HomeScreenState? = initialState

    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost, comments)
    }

    fun closeComments() {
        _screenState.value = savedState
    }

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return
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
        _screenState.value = HomeScreenState.Posts(newPosts)
    }

    fun delete(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return
        val newPosts = currentState.posts.filter { it.id != feedPost.id }
        _screenState.value = HomeScreenState.Posts(newPosts)
    }
}