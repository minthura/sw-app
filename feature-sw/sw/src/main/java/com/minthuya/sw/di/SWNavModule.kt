package com.minthuya.sw.di

import com.minthuya.component.AppScope
import com.minthuya.sw.navigator.SWNavigatorImpl
import com.minthuya.swkit.SWNavigator
import dagger.Module
import dagger.Provides

@Module
object SWNavModule {
    @Provides
    @AppScope
    fun provideSampleNavigator(): SWNavigator = SWNavigatorImpl()
}