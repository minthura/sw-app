package com.minthuya.coreappsdk.di

import com.minthuya.component.AppScope
import com.minthuya.network.NetworkConfig
import dagger.Module
import dagger.Provides

@Module
class CoreAppModule(
    private val networkConfig: NetworkConfig
) {
    @Provides
    @AppScope
    fun provideNetworkConfig() = networkConfig
}