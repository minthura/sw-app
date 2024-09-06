package com.minthuya.sw.data.service.impl

import android.content.Context
import com.minthuya.localdbkit.LocalDbKit
import com.minthuya.sw.data.model.DownloadState
import com.minthuya.sw.data.model.PersistState
import com.minthuya.sw.data.model.SyncResult
import com.minthuya.sw.data.model.UnzipState
import com.minthuya.sw.data.repository.SWRepository
import com.minthuya.sw.data.service.DownloadFileService
import com.minthuya.sw.data.service.PersistStationsDbService
import com.minthuya.sw.data.service.SyncStationsService
import com.minthuya.sw.data.service.UnzipFileService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.InputStream

class SyncStationsServiceImpl(
    private val context: Context,
    private val downloadFileService: DownloadFileService,
    private val unzipFileService: UnzipFileService,
    private val persistStationsDbService: PersistStationsDbService,
    private val repository: SWRepository,
): SyncStationsService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun sync(force: Boolean): Flow<SyncResult> =
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
                            unzipFileService.unzipFile(downloadState.file).flatMapConcat { unzipState ->
                                when (unzipState) {
                                    is UnzipState.Unzipping -> {
                                        flowOf(SyncResult.Unzipping(unzipState.progress))
                                    }
                                    is UnzipState.Finished -> {
                                        // Persist the file to DB
                                        persistStationsDbService(File(unzipState.dir, "nxa24.xlsx").inputStream())
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
}