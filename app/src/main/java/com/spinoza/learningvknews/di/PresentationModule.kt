package com.spinoza.learningvknews.di

import com.spinoza.learningvknews.presentation.feature.comments.viewmodel.CommentsViewModel
import com.spinoza.learningvknews.presentation.feature.main.viewmodel.MainViewModel
import com.spinoza.learningvknews.presentation.feature.news.viewmodel.NewsFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        MainViewModel(
            getAuthStateUseCase = get(),
            checkAuthStateUseCase = get()
        )
    }

    viewModel {
        NewsFeedViewModel(
            getRecommendationsUseCase = get(),
            loadNextDataUseCase = get(),
            changeLikeStatusUseCase = get(),
            deletePostUseCase = get(),
        )
    }

    viewModel { feedPost ->
        CommentsViewModel(
            feedPost = feedPost.get(),
            getCommentsUseCase = get()
        )
    }
}