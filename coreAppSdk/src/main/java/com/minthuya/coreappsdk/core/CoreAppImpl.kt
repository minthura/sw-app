package com.minthuya.coreappsdk.core

import android.app.Activity
import android.app.Application
import android.content.Context
import com.minthuya.coreappsdk.di.CoreAppModule
import com.minthuya.coreappsdk.di.DaggerCoreAppComponent
import com.minthuya.navigationkit.CoreAppNavType
import com.minthuya.navigationkit.CoreAppNavigator
import com.minthuya.network.NetworkConfig
import javax.inject.Inject

class CoreAppImpl(
    private val context: Application,
    private val baseUrl: String
) : CoreApp {

    @Inject
    lateinit var navigator: CoreAppNavigator

    private val component by lazy {
        DaggerCoreAppComponent.factory().create(
            context = context,
            coreAppModule = CoreAppModule(
                networkConfig = NetworkConfig(
                    baseUrl = baseUrl
                )
            )
        )
    }

    override fun create() {
        component.inject(this)
    }

    override fun context(): Context = context.baseContext
    override fun navigateTo(context: Activity, type: CoreAppNavType) = navigator.navigateTo(context, type)

    @Suppress("UNCHECKED_CAST")
    override fun <T> provide(dependency: Class<T>): T {
        return component as T
    }
}