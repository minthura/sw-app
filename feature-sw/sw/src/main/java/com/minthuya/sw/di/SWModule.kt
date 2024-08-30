package com.minthuya.sw.di

import android.content.Context
import com.minthuya.component.FragmentScope
import com.minthuya.localdbkit.LocalDbKit
import com.minthuya.sw.data.service.SyncStationsService
import com.minthuya.sw.data.service.impl.SyncStationsServiceImpl
import dagger.Module
import dagger.Provides

@Module
object SWModule {
    @Provides
    @FragmentScope
    fun provideReadFromExcelService(
        context: Context,
        localDbKit: LocalDbKit
    ): SyncStationsService =
        SyncStationsServiceImpl(
            context,
            localDbKit
        )
}