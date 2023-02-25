package com.spinoza.learningvknews.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.StatisticItem

class MainViewModel : ViewModel() {
    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    fun updateCount(statisticItem: StatisticItem) {
        feedPost.value?.let { post ->
            val oldStatistics = post.statistics
            val newStatistics = oldStatistics.toMutableList().apply {
                replaceAll { oldItem ->
                    if (oldItem.type == statisticItem.type) {
                        oldItem.copy(count = oldItem.count + 1)
                    } else {
                        oldItem
                    }
                }
            }
            _feedPost.value = post.copy(statistics = newStatistics)
        }
    }
}
