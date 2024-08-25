package com.minthuya.sample.di

import androidx.lifecycle.ViewModelStoreOwner
import com.minthuya.component.FragmentScope
import com.minthuya.component.viewmodel.getViewModel
import com.minthuya.networkkit.NetworkKit
import com.minthuya.sample.data.api.SampleApi
import com.minthuya.sample.data.datasource.SampleDataSource
import com.minthuya.sample.data.datasource.impl.SampleDataSourceImpl
import com.minthuya.sample.data.repository.SampleRepository
import com.minthuya.sample.data.repository.impl.SampleRepositoryImpl
import com.minthuya.sample.domain.GetWeatherUseCase
import com.minthuya.sample.domain.GetWeatherUseCaseImpl
import com.minthuya.sample.ui.SampleViewModel
import dagger.Module
import dagger.Provides

@Module
class SampleModule {
    @Provides
    @FragmentScope
    fun provideSampleViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        getWeatherUseCase: GetWeatherUseCase
    ): SampleViewModel = SampleViewModel(
        getWeatherUseCase
    ).getViewModel(viewModelStoreOwner)

    @Provides
    @FragmentScope
    fun provideSampleApi(
        networkKit: NetworkKit
    ): SampleApi = networkKit.createService(SampleApi::class.java)

    @Provides
    @FragmentScope
    fun provideSampleDatasource(
        sampleApi: SampleApi
    ): SampleDataSource = SampleDataSourceImpl(
        sampleApi
    )

    @Provides
    @FragmentScope
    fun provideSampleRepository(
        sampleDataSource: SampleDataSource
    ): SampleRepository = SampleRepositoryImpl(
        sampleDataSource
    )

    @Provides
    @FragmentScope
    fun provideSampleUseCase(
        sampleRepository: SampleRepository
    ): GetWeatherUseCase = GetWeatherUseCaseImpl(
        sampleRepository
    )
}