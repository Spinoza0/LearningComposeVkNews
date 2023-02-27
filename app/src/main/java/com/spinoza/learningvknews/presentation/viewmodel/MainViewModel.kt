package com.spinoza.learningvknews.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.StatisticItem

class MainViewModel : ViewModel() {
    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val oldStatistics = feedPost.statistics
        val oldFeedPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
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

        _feedPosts.value = oldFeedPosts.apply {
            replaceAll {
                if (it.id == feedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
    }

    fun delete(feedPost: FeedPost) {
        val modifiedList = feedPosts.value?.toMutableList() ?: mutableListOf()
        _feedPosts.value = modifiedList.apply {
            remove(feedPost)
        }
    }
}
