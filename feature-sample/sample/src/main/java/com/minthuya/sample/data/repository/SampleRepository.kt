package com.minthuya.sample.data.repository

import com.minthuya.sample.data.dtos.Weather
import kotlinx.coroutines.flow.Flow

interface SampleRepository {
    fun getWeatherData(): Flow<Weather>
}