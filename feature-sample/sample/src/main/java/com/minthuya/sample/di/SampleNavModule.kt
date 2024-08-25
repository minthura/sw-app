package com.minthuya.sample.di

import com.minthuya.component.AppScope
import com.minthuya.sample.navigator.SampleNavigatorImpl
import com.minthuya.samplekit.SampleNavigator
import dagger.Module
import dagger.Provides

@Module
object SampleNavModule {
    @Provides
    @AppScope
    fun provideSampleNavigator(): SampleNavigator = SampleNavigatorImpl()
}