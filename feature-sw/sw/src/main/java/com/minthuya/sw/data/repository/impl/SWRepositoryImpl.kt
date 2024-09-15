package com.minthuya.sw.data.repository.impl

import com.minthuya.localdbkit.dao.SettingDao
import com.minthuya.localdbkit.dao.StationDao
import com.minthuya.localdbkit.entity.Setting
import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.data.datasource.SWDataSource
import com.minthuya.sw.data.repository.SWRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

class SWRepositoryImpl(
    private val swDataSource: SWDataSource,
    private val stationDao: StationDao,
    private val settingDao: SettingDao,
    private val scope: CoroutineDispatcher = Dispatchers.IO
) : SWRepository {
    override fun getStations(offset: Int, limit: Int, language: String, station: String?): Flow<List<Station>> {
        return flow {
            emit(swDataSource.getShortWaveSchedules(offset, limit, language, station))
        }.flowOn(scope)
    }

    override fun getLanguages(): Flow<List<String>> {
        return flow {
            emit(swDataSource.getLanguages())
        }.flowOn(scope)
    }

    override fun downloadNxa24Zip(): Flow<ResponseBody> {
        return flow {
            emit(swDataSource.downloadNxa24Zip())
        }.flowOn(scope)
    }

    override fun hasPersistedInDb(): Flow<Boolean> {
        return flow {
            emit(stationDao.stationsCount() > 0)
        }.flowOn(scope)
    }

    override fun setSettings(settings: List<Setting>): Flow<Boolean> {
        return flow {
            emit(
                settingDao.setSettings(settings)
            )
        }.flowOn(scope)
    }

    override fun getSettings(): Flow<List<Setting>> {
        return flow {
            emit(settingDao.getSettings())
        }.flowOn(scope)
    }
}