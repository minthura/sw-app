package com.minthuya.sw.data.service

import com.minthuya.sw.data.model.DownloadState
import kotlinx.coroutines.flow.Flow

interface DownloadFileService {
    operator fun invoke(): Flow<DownloadState>
}
