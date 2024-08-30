package com.minthuya.sw.data.datasource.impl

import com.minthuya.sw.data.datasource.SWDataSource
import com.minthuya.sw.data.dtos.Schedule

class SWLocalDataSource: SWDataSource {
    override suspend fun getShortWaveSchedules(): List<Schedule> {
        TODO("Not yet implemented")
    }
}