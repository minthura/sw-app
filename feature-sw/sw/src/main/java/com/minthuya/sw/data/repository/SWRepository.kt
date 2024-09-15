package com.minthuya.sw.data.repository

import com.minthuya.localdbkit.entity.Setting
import com.minthuya.localdbkit.entity.Station
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface SWRepository {
    fun getStations(offset: Int, limit: Int, language: String, station: String?): Flow<List<Station>>
    fun getLanguages(): Flow<List<String>>
    fun downloadNxa24Zip(): Flow<ResponseBody>
    fun hasPersistedInDb(): Flow<Boolean>
    fun setSettings(settings: List<Setting>): Flow<Boolean>
    fun getSettings(): Flow<List<Setting>>
}