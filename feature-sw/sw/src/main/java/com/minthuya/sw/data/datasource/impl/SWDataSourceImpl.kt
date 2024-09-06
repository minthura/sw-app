package com.minthuya.sw.data.datasource.impl

import com.minthuya.localdbkit.dao.StationDao
import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.data.api.SWApi
import com.minthuya.sw.data.datasource.SWDataSource
import okhttp3.ResponseBody

class SWDataSourceImpl(
    private val stationDao: StationDao,
    private val swApi: SWApi
) : SWDataSource {
    override suspend fun getShortWaveSchedules(
        offset: Int,
        limit: Int,
        language: String,
        station: String?
    ): List<Station> =
        stationDao.getStations(limit, offset, language, station)

    override suspend fun getLanguages(): List<String> =
        stationDao.getLanguages()

    override suspend fun downloadNxa24Zip(): ResponseBody =
        swApi.downloadNxa24Zip()
}