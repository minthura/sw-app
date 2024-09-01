package com.minthuya.sw.data.datasource

import com.minthuya.localdbkit.entity.Station

interface SWDataSource {
    suspend fun getShortWaveSchedules(offset: Int, limit: Int, language: String): List<Station>
    suspend fun getLanguages(): List<String>
}