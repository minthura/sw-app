package com.minthuya.sw.data.service

import com.minthuya.sw.data.model.UnzipState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UnzipFileService {
    operator fun invoke(zipFile: File): Flow<UnzipState>
}