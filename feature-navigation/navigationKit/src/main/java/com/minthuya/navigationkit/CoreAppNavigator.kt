package com.minthuya.navigationkit

import android.content.Context

interface CoreAppNavigator {
    fun navigateTo(context: Context, type: CoreAppNavType)
}