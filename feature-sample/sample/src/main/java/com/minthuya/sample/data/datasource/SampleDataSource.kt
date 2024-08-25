package com.minthuya.sample.data.datasource

import com.minthuya.sample.data.dtos.Weather
import retrofit2.Response

interface SampleDataSource {
    suspend fun getWeatherData(): Response<Weather>
}