package com.minthuya.navigation.di

import com.minthuya.component.AppScope
import com.minthuya.navigation.CoreAppNavigatorImpl
import com.minthuya.navigationkit.CoreAppNavigator
import com.minthuya.samplekit.SampleNavigator
import dagger.Module
import dagger.Provides

@Module
object CoreAppNavModule {
    @Provides
    @AppScope
    fun provideCoreAppNavigator(
        sampleNavigator: SampleNavigator,
    ): CoreAppNavigator = CoreAppNavigatorImpl(
        sampleNavigator = sampleNavigator
    )
}