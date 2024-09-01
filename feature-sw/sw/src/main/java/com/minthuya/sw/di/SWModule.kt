package com.minthuya.sw.di

import android.content.Context
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.minthuya.component.FragmentScope
import com.minthuya.component.viewmodel.getViewModel
import com.minthuya.localdbkit.LocalDbKit
import com.minthuya.localdbkit.dao.StationDao
import com.minthuya.sw.data.datasource.SWDataSource
import com.minthuya.sw.data.datasource.impl.SWLocalDataSource
import com.minthuya.sw.data.repository.SWRepository
import com.minthuya.sw.data.repository.impl.SWRepositoryImpl
import com.minthuya.sw.data.service.SyncStationsService
import com.minthuya.sw.data.service.impl.SyncStationsServiceImpl
import com.minthuya.sw.domain.usecase.GetSWLanguagesUseCase
import com.minthuya.sw.domain.usecase.GetSWLanguagesUseCaseImpl
import com.minthuya.sw.domain.usecase.GetSWStationsUseCase
import com.minthuya.sw.domain.usecase.GetSWStationsUseCaseImpl
import com.minthuya.sw.navigator.SWInternalNavigator
import com.minthuya.sw.navigator.SWInternalNavigatorImpl
import com.minthuya.sw.ui.list.StationsListViewModel
import com.minthuya.sw.ui.sync.SyncStationsViewModel
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

    @Provides
    @FragmentScope
    fun provideStationDao(
        localDbKit: LocalDbKit
    ): StationDao = localDbKit.getDb().stationDao()

    @Provides
    @FragmentScope
    fun provideSWDataSource(
        stationDao: StationDao
    ): SWDataSource =
        SWLocalDataSource(
            stationDao
        )

    @Provides
    @FragmentScope
    fun provideSWRepository(
        swDataSource: SWDataSource
    ): SWRepository =
        SWRepositoryImpl(
            swDataSource
        )

    @Provides
    @FragmentScope
    fun provideGetSWStationsUseCase(
        swRepository: SWRepository
    ): GetSWStationsUseCase =
        GetSWStationsUseCaseImpl(swRepository)

    @Provides
    @FragmentScope
    fun provideGetSWLanguagesUseCase(
        swRepository: SWRepository
    ): GetSWLanguagesUseCase =
        GetSWLanguagesUseCaseImpl(swRepository)

    @Provides
    @FragmentScope
    fun provideEntryViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        syncStationsService: SyncStationsService
    ): SyncStationsViewModel =
        SyncStationsViewModel(
            syncStationsService
        ).getViewModel(viewModelStoreOwner)

    @Provides
    @FragmentScope
    fun provideStationsListViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        getSWStationsUseCase: GetSWStationsUseCase,
        getSWLanguagesUseCase: GetSWLanguagesUseCase
    ): StationsListViewModel =
        StationsListViewModel(
            getSWStationsUseCase,
            getSWLanguagesUseCase
        ).getViewModel(viewModelStoreOwner)

    @Provides
    @FragmentScope
    fun provideSWInternalNavigator(
        navController: NavController
    ): SWInternalNavigator =
        SWInternalNavigatorImpl(
            navController
        )

}