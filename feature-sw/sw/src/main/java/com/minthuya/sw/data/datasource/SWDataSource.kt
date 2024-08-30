package com.minthuya.sw.data.datasource

import com.minthuya.sw.data.dtos.Schedule

interface SWDataSource {
    suspend fun getShortWaveSchedules(): List<Schedule>
}