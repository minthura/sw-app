package com.minthuya.sw.data.datasource

import com.minthuya.localdbkit.entity.Station
import okhttp3.ResponseBody

interface SWDataSource {
    suspend fun getShortWaveSchedules(offset: Int, limit: Int, language: String, station: String?): List<Station>
    suspend fun getLanguages(): List<String>
    suspend fun downloadNxa24Zip(): ResponseBody
}