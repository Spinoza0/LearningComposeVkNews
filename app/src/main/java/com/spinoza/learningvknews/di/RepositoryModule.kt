package com.spinoza.learningvknews.di

import com.spinoza.learningvknews.data.repository.NewsFeedRepositoryImpl
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {

    single<NewsFeedRepository> {
        NewsFeedRepositoryImpl(
            apiService = get(),
            vkPreferencesKeyValueStorage = get(),
            repositoryScope = CoroutineScope(Dispatchers.IO)
        )
    }

    single {
        VKPreferencesKeyValueStorage(context = get())
    }
}