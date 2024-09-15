package com.minthuya.sw.domain.usecase

import com.minthuya.localdbkit.entity.Setting
import com.minthuya.sw.data.repository.SWRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsUseCase {
    fun setSettings(map: Map<String, String>): Flow<Boolean>
    fun getSettings(): Flow<Map<String, String>>
}

class SettingsUseCaseImpl(
    private val swRepository: SWRepository
) : SettingsUseCase {
    override fun setSettings(map: Map<String, String>): Flow<Boolean> =
        swRepository.setSettings(
            map.map {
                Setting(key = it.key, value = it.value)
            }
        )

    override fun getSettings(): Flow<Map<String, String>> =
        swRepository.getSettings().map {
            it.associate { it.key to it.value }
        }
}