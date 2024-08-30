package com.minthuya.navigation

import android.content.Context
import com.minthuya.navigationkit.CoreAppNavType
import com.minthuya.navigationkit.CoreAppNavigator
import com.minthuya.samplekit.SampleNavigator
import com.minthuya.swkit.SWNavigator

class CoreAppNavigatorImpl(
    private val sampleNavigator: SampleNavigator,
    private val swNavigator: SWNavigator
) : CoreAppNavigator {
    override fun navigateTo(context: Context, type: CoreAppNavType) {
        when (type) {
            CoreAppNavType.FEATURE_SAMPLE -> sampleNavigator.navigateToSampleFeature(context)
            CoreAppNavType.FEATURE_SW -> swNavigator.navigateToSW(context)
        }
    }
}