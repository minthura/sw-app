package com.minthuya.sw.navigator

import android.content.Context
import com.minthuya.sw.SwActivity
import com.minthuya.swkit.SWNavigator

class SWNavigatorImpl : SWNavigator {
    override fun navigateToSW(context: Context) {
        SwActivity.start(context)
    }
}