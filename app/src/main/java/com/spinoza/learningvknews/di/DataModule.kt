package com.spinoza.learningvknews.di

import com.spinoza.learningvknews.data.network.ApiFactory
import com.spinoza.learningvknews.data.network.TokenStorage
import com.spinoza.learningvknews.data.network.TokenStorageImpl
import com.spinoza.learningvknews.data.repository.NewsFeedRepositoryImpl
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        ApiFactory.apiService
    }

    single<TokenStorage> {
        TokenStorageImpl
    }

    single<NewsFeedRepository> {
        NewsFeedRepositoryImpl(
            context = get(),
            tokenStorage = get(),
            apiService = get()
        )
    }
}