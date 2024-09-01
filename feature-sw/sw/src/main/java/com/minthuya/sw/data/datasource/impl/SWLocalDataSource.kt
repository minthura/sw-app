package com.minthuya.sw.data.datasource.impl

import com.minthuya.localdbkit.dao.StationDao
import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.data.datasource.SWDataSource

class SWLocalDataSource(
    private val stationDao: StationDao
): SWDataSource {
    override suspend fun getShortWaveSchedules(offset: Int, limit: Int, language: String, station: String?): List<Station> =
        stationDao.getStations(limit, offset, language, station)

    override suspend fun getLanguages(): List<String> =
        stationDao.getLanguages()
}