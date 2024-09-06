package com.minthuya.codex.di

import android.app.Application
import android.content.Context
import com.minthuya.codex.BuildConfig
import com.minthuya.component.AppScope
import com.minthuya.coreappsdk.constant.CoreAppConstant
import com.minthuya.coreappsdk.core.CoreApp
import com.minthuya.coreappsdk.core.CoreAppImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object ApplicationModule {

    @Provides
    @AppScope
    fun provideContext(application: Application): Context = application

    @Provides
    @AppScope
    fun provideCoreApp(
        context: Application,
        @Named(CoreAppConstant.BASE_MS_URL) baseUrl: String,
        @Named(CoreAppConstant.SW_BASE_MS_URL) swBaseUrl: String,
    ): CoreApp {
        val coreApp = CoreAppImpl(
            context = context,
            baseUrl = baseUrl,
            swBaseUrl = swBaseUrl
        )
        coreApp.create()
        return coreApp
    }

    @Provides
    @AppScope
    @Named(CoreAppConstant.BASE_MS_URL)
    fun provideBaseUrl(): String = BuildConfig.MS_URL

    @Provides
    @AppScope
    @Named(CoreAppConstant.SW_BASE_MS_URL)
    fun provideSwBaseUrl(): String = BuildConfig.SW_BASE_URL
}