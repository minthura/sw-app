package com.minthuya.navigation.di

import com.minthuya.component.AppScope
import com.minthuya.navigation.CoreAppNavigatorImpl
import com.minthuya.navigationkit.CoreAppNavigator
import com.minthuya.samplekit.SampleNavigator
import com.minthuya.swkit.SWNavigator
import dagger.Module
import dagger.Provides

@Module
object CoreAppNavModule {
    @Provides
    @AppScope
    fun provideCoreAppNavigator(
        sampleNavigator: SampleNavigator,
        swNavigator: SWNavigator
    ): CoreAppNavigator = CoreAppNavigatorImpl(
        sampleNavigator = sampleNavigator,
        swNavigator = swNavigator
    )
}