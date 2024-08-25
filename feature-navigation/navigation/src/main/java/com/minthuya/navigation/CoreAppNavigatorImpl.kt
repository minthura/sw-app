package com.minthuya.navigation

import android.content.Context
import com.minthuya.navigationkit.CoreAppNavType
import com.minthuya.navigationkit.CoreAppNavigator
import com.minthuya.samplekit.SampleNavigator

class CoreAppNavigatorImpl(
    private val sampleNavigator: SampleNavigator
) : CoreAppNavigator {
    override fun navigateTo(context: Context, type: CoreAppNavType) {
        when (type) {
            CoreAppNavType.FEATURE_SAMPLE -> sampleNavigator.navigateToSampleFeature(context)
        }
    }
}