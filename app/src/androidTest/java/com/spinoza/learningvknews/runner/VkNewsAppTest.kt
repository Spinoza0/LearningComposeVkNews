package com.spinoza.learningvknews.runner

import com.spinoza.learningvknews.di.domainModule
import com.spinoza.learningvknews.di.networkModule
import com.spinoza.learningvknews.di.presentationModule
import com.spinoza.learningvknews.di.repositoryModuleTest
import com.spinoza.learningvknews.presentation.feature.main.VkNewsApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VkNewsAppTest : VkNewsApp() {

    override fun startDependencyInjection() {
        startKoin {
            androidContext(this@VkNewsAppTest)
            modules(listOf(presentationModule, domainModule, networkModule, repositoryModuleTest))
        }
    }
}