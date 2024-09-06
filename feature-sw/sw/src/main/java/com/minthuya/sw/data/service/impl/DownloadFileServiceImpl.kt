package com.minthuya.sw.data.service.impl

import android.content.Context
import com.minthuya.sw.data.model.DownloadState
import com.minthuya.sw.data.repository.SWRepository
import com.minthuya.sw.data.service.DownloadFileService
import com.minthuya.sw.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DownloadFileServiceImpl(
    private val context: Context,
    private val swRepository: SWRepository,
    private val scope: CoroutineDispatcher = Dispatchers.IO,
) : DownloadFileService {
    override fun invoke(): Flow<DownloadState> =
        flow {
            swRepository.downloadNxa24Zip().collect { responseBody ->
                val zipFilename = "nxa24.zip"
                val zipFile = java.io.File(context.filesDir, zipFilename)
                responseBody.byteStream().use { byteStream ->
                    zipFile.outputStream().use { outputStream ->
                        val totalBytes = responseBody.contentLength()
                        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                        var progressBytes = 0.0
                        var bytes = byteStream.read(buffer)
                        while (bytes >= 0) {
                            outputStream.write(buffer, 0, bytes)
                            progressBytes += bytes
                            bytes = byteStream.read(buffer)
                            emit(DownloadState.Downloading((progressBytes * Constants.INT_100) / totalBytes))
                        }
                        emit(DownloadState.Finished(zipFile))
                    }
                }
            }
        }.flowOn(scope)
}