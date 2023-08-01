package com.spinoza.learningvknews.presentation.feature.main

import android.app.Application
import com.spinoza.learningvknews.di.domainModule
import com.spinoza.learningvknews.di.networkModule
import com.spinoza.learningvknews.di.presentationModule
import com.spinoza.learningvknews.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class VkNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startDependencyInjection()
    }

    open fun startDependencyInjection() {
        startKoin {
            androidContext(this@VkNewsApp)
            modules(listOf(presentationModule, domainModule, networkModule, repositoryModule))
        }
    }
}