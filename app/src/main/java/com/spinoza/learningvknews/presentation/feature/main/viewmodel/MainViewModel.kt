package com.spinoza.learningvknews.presentation.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.learningvknews.domain.NewsFeedRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NewsFeedRepository) : ViewModel() {

    val authState = repository.authState

    fun preformAuthResult() {
        viewModelScope.launch {
            repository.checkAuthState()
        }
    }
}