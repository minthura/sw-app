package com.minthuya.coreappsdk.core

import android.app.Activity
import android.content.Context
import com.minthuya.navigationkit.CoreAppNavType

interface CoreApp {
    fun create()
    fun context(): Context
    fun navigateTo(context: Activity, type: CoreAppNavType)
    fun <T> provide(dependency: Class<T>): T
}