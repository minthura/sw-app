package com.minthuya.localdb.di

import android.content.Context
import com.minthuya.component.AppScope
import com.minthuya.localdb.LocalDbKitImpl
import com.minthuya.localdbkit.LocalDbKit
import dagger.Module
import dagger.Provides

@Module
class LocalDbModule {
    @Provides
    @AppScope
    fun provideLocalDbKit(
        context: Context
    ): LocalDbKit =
        LocalDbKitImpl(
            context
        )
}