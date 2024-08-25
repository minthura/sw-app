package com.minthuya.sample.navigator

import android.content.Context
import com.minthuya.sample.SampleActivity
import com.minthuya.samplekit.SampleNavigator

class SampleNavigatorImpl : SampleNavigator {
    override fun navigateToSampleFeature(context: Context) {
        SampleActivity.start(context)
    }
}