package com.minthuya.sw.data.model

import java.io.File

sealed class SyncResult(val progress: Double = 0.0, error: String? = null, val file: File? = null) {
    data object Success : SyncResult()
    class DownloadingZip(progress: Double) : SyncResult(progress = progress)
    class DownloadSuccess(file: File) : SyncResult(file = file)
    class Unzipping(progress: Double) : SyncResult(progress = progress)
    data object Indeterminant : SyncResult()
    data object ReadingNxa24File : SyncResult()
    class LoadingChannels(progress: Double) : SyncResult(progress = progress)
    class Error(error: String? = null) : SyncResult(error = error)
}