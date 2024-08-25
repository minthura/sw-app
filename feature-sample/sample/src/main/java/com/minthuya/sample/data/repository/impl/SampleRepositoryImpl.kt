package com.minthuya.sample.data.repository.impl

import com.minthuya.networkkit.safeResponse
import com.minthuya.sample.data.datasource.SampleDataSource
import com.minthuya.sample.data.dtos.Weather
import com.minthuya.sample.data.repository.SampleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SampleRepositoryImpl(
    private val sampleDataSource: SampleDataSource,
    private val scope: CoroutineDispatcher = Dispatchers.IO
) : SampleRepository {
    override fun getWeatherData(): Flow<Weather> {
        return flow {
            emit(sampleDataSource.getWeatherData().safeResponse())
        }.flowOn(scope)
    }
}