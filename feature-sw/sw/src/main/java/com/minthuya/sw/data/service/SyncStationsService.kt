package com.minthuya.sw.data.service

import com.minthuya.sw.data.model.SyncResult
import kotlinx.coroutines.flow.Flow

interface SyncStationsService {
    fun autoSync(force: Boolean = false): Flow<SyncResult>
    fun syncLocal(): Flow<SyncResult>
}