package com.spinoza.learningvknews.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spinoza.learningvknews.data.network.ApiService
import com.spinoza.learningvknews.data.repository.NewsFeedRepositoryImpl
import com.spinoza.learningvknews.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {

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

    single {
        val contentType = MEDIA_TYPE_JSON.toMediaType()
        val jsonConverter = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverter.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()

        retrofit.create() as ApiService
    }
}

private const val BASE_URL = "https://api.vk.com/method/"
private const val MEDIA_TYPE_JSON = "application/json"
private const val TIME_OUT_SECONDS = 15L