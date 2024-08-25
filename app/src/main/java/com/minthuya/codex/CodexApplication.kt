package com.minthuya.codex

import android.app.Application
import com.minthuya.codex.di.ApplicationComponent
import com.minthuya.codex.di.DaggerApplicationComponent
import com.minthuya.component.RootComponentProvider
import com.minthuya.component.RootParentComponentProvider
import com.minthuya.coreappsdk.core.CoreApp
import javax.inject.Inject

class CodexApplication :
    Application(),
    RootComponentProvider,
    RootParentComponentProvider {

    @Inject
    lateinit var coreApp: CoreApp

    private lateinit var rootDi: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        rootDi = DaggerApplicationComponent.factory().create(this)
        rootDi.inject(this)
    }

    override fun <T> provide(dependency: Class<T>): T = coreApp.provide(dependency)

    @Suppress("UNCHECKED_CAST")
    override fun <T> root(dependency: Class<T>): T = rootDi as T
}