package com.minthuya.sw.data.service

import kotlinx.coroutines.flow.Flow

interface SyncStationsService {
    fun read(): Flow<Double>
}