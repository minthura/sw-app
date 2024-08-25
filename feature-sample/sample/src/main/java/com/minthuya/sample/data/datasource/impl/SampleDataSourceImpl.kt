package com.minthuya.sample.data.datasource.impl

import com.minthuya.sample.data.api.SampleApi
import com.minthuya.sample.data.datasource.SampleDataSource
import com.minthuya.sample.data.dtos.Weather
import retrofit2.Response

class SampleDataSourceImpl(
    private val sampleApi: SampleApi
) : SampleDataSource {
    override suspend fun getWeatherData(): Response<Weather> {
        return sampleApi.getWeatherData()
    }
}