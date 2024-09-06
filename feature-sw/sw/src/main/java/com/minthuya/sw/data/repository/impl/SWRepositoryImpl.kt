package com.minthuya.sw.data.repository.impl

import com.minthuya.localdbkit.LocalDbKit
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
    private val localDbKit: LocalDbKit,
    private val scope: CoroutineDispatcher = Dispatchers.IO
): SWRepository {
    override fun getStations(offset: Int, limit: Int, language: String, station: String?): Flow<List<Station>> {
        return flow {
            emit(swDataSource.getShortWaveSchedules(offset, limit, language, station))
        }.flowOn(scope)
    }

    override fun getLanguages(): Flow<List<String>> {
        return  flow {
            emit(swDataSource.getLanguages())
        }.flowOn(scope)
    }

    override fun downloadNxa24Zip(): Flow<ResponseBody> {
        return  flow {
            emit(swDataSource.downloadNxa24Zip())
        }.flowOn(scope)
    }

    override fun hasPersistedInDb(): Flow<Boolean> {
        return  flow {
            val db = localDbKit.getDb()
            emit(db.stationDao().stationsCount() > 0)
            println("hasPersistedInDb")
        }.flowOn(scope)
    }
}