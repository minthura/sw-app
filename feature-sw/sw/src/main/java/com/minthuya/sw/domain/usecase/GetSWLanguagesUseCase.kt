package com.minthuya.sw.domain.usecase

import com.minthuya.sw.data.repository.SWRepository
import kotlinx.coroutines.flow.Flow

interface GetSWLanguagesUseCase {
    operator fun invoke(): Flow<List<String>>
}

class GetSWLanguagesUseCaseImpl(
    private val swRepository: SWRepository
): GetSWLanguagesUseCase {
    override fun invoke(): Flow<List<String>> =
        swRepository.getLanguages()
}