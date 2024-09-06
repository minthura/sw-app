package com.minthuya.sw.data.service.impl

import android.content.Context
import com.minthuya.sw.data.model.DownloadState
import com.minthuya.sw.data.model.PersistState
import com.minthuya.sw.data.model.SyncResult
import com.minthuya.sw.data.model.UnzipState
import com.minthuya.sw.data.repository.SWRepository
import com.minthuya.sw.data.service.DownloadFileService
import com.minthuya.sw.data.service.PersistStationsDbService
import com.minthuya.sw.data.service.SyncStationsService
import com.minthuya.sw.data.service.UnzipFileService
import com.minthuya.sw.util.FileConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import java.io.File

class SyncStationsServiceImpl(
    private val context: Context,
    private val downloadFileService: DownloadFileService,
    private val unzipFileService: UnzipFileService,
    private val persistStationsDbService: PersistStationsDbService,
    private val repository: SWRepository,
) : SyncStationsService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun autoSync(force: Boolean): Flow<SyncResult> =
        repository.hasPersistedInDb().flatMapConcat { hasPersisted ->
            if (!hasPersisted) {
                // Download the file
                downloadFileService().flatMapConcat { downloadState ->
                    when (downloadState) {
                        is DownloadState.Downloading -> {
                            flowOf(SyncResult.DownloadingZip(downloadState.progress))
                        }
                        is DownloadState.Finished -> {
                            // Unzip the downloaded file
                            unzipFileService(downloadState.file).flatMapConcat { unzipState ->
                                when (unzipState) {
                                    is UnzipState.Unzipping -> {
                                        flowOf(SyncResult.Unzipping(unzipState.progress))
                                    }
                                    is UnzipState.Finished -> {
                                        // Persist the file to DB
                                        persistStationsDbService(
                                            File(unzipState.dir, FileConstants.NXA24_XLSX).inputStream()
                                        )
                                            .flatMapConcat { persistState ->
                                                when (persistState) {
                                                    is PersistState.Persisting -> {
                                                        flowOf(SyncResult.LoadingChannels(persistState.progress))
                                                    }
                                                    PersistState.ReadingFile -> {
                                                        flowOf(SyncResult.ReadingNxa24File)
                                                    }
                                                    PersistState.Success -> {
                                                        flowOf(SyncResult.Success)
                                                    }
                                                }
                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // If already persisted, return success
                flowOf(SyncResult.Success)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun syncLocal(): Flow<SyncResult> =
        persistStationsDbService(context.assets.open(FileConstants.NXA24_XLSX))
            .flatMapConcat { persistState ->
                when (persistState) {
                    is PersistState.Persisting -> {
                        flowOf(SyncResult.LoadingChannels(persistState.progress))
                    }
                    PersistState.ReadingFile -> {
                        flowOf(SyncResult.ReadingNxa24File)
                    }
                    PersistState.Success -> {
                        flowOf(SyncResult.Success)
                    }
                }
            }
}