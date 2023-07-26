package com.spinoza.learningvknews.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val BASE_URL = "https://api.vk.com/method/"
    private const val MEDIA_TYPE_JSON = "application/json"
    private const val TIME_OUT_SECONDS = 15L

    private val contentType = MEDIA_TYPE_JSON.toMediaType()
    private val jsonConverter = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(jsonConverter.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}