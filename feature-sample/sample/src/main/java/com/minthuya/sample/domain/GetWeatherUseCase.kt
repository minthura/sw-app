package com.minthuya.sample.domain

import com.minthuya.sample.data.dtos.Weather
import com.minthuya.sample.data.repository.SampleRepository
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    operator fun invoke(): Flow<Weather>
}

class GetWeatherUseCaseImpl(
    private val sampleRepository: SampleRepository
) : GetWeatherUseCase {
    override fun invoke(): Flow<Weather> {
        return sampleRepository.getWeatherData()
    }
}