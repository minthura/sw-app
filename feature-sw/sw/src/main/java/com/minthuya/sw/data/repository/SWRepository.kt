package com.minthuya.sw.data.repository

import com.minthuya.localdbkit.entity.Station
import kotlinx.coroutines.flow.Flow

interface SWRepository {
    fun getStations(offset: Int, limit: Int, language: String): Flow<List<Station>>
    fun getLanguages(): Flow<List<String>>
}