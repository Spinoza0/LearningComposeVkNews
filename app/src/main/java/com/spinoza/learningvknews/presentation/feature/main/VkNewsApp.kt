package com.spinoza.learningvknews.presentation.feature.main

import android.app.Application
import com.spinoza.learningvknews.di.dataModule
import com.spinoza.learningvknews.di.domainModule
import com.spinoza.learningvknews.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VkNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VkNewsApp)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}