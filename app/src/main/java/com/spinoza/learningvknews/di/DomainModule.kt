package com.spinoza.learningvknews.di

import com.spinoza.learningvknews.domain.usecase.ChangeLikeStatusUseCase
import com.spinoza.learningvknews.domain.usecase.CheckAuthStateUseCase
import com.spinoza.learningvknews.domain.usecase.DeletePostUseCase
import com.spinoza.learningvknews.domain.usecase.GetAuthStateUseCase
import com.spinoza.learningvknews.domain.usecase.GetCommentsUseCase
import com.spinoza.learningvknews.domain.usecase.GetRecommendationsUseCase
import com.spinoza.learningvknews.domain.usecase.LoadNextDataUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        ChangeLikeStatusUseCase(repository = get())
    }

    factory {
        CheckAuthStateUseCase(repository = get())
    }

    factory {
        DeletePostUseCase(repository = get())
    }

    factory {
        GetAuthStateUseCase(repository = get())
    }

    factory {
        GetCommentsUseCase(repository = get())
    }

    factory {
        GetRecommendationsUseCase(repository = get())
    }

    factory {
        LoadNextDataUseCase(repository = get())
    }
}