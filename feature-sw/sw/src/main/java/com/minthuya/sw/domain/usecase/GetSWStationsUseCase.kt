package com.minthuya.sw.domain.usecase

import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.data.repository.SWRepository
import kotlinx.coroutines.flow.Flow

interface GetSWStationsUseCase {
    operator fun invoke(offset: Int, limit: Int = 30, language: String): Flow<List<Station>>
}

class GetSWStationsUseCaseImpl(
    private val swRepository: SWRepository
): GetSWStationsUseCase {
    override fun invoke(offset: Int, limit: Int, language: String): Flow<List<Station>> =
        swRepository.getStations(offset, limit, language)

}