package com.minthuya.component

import android.content.Context
import android.util.Log

interface RootComponentProvider {
    fun <T> provide(dependency: Class<T>): T
}

interface RootParentComponentProvider {
    fun <T> root(dependency: Class<T>): T
}

inline fun <reified T> Context.parent(): T {
    return if (applicationContext is RootComponentProvider) {
        (applicationContext as RootComponentProvider).provide(T::class.java)
    } else {
        error("Please add your dependency in FeatureComponent")
    }
}

inline fun <reified T> Context.rootParent(): T {
    return if (applicationContext is RootParentComponentProvider) {
        Log.d("MinCheck", T::class.java.name)
        (applicationContext as RootParentComponentProvider).root(T::class.java)
    } else {
        parent()
    }
}
