package com.minthuya.sw.data.model

sealed class SyncResult(val progress: Double? = null, error: String? = null) {
    class Success() : SyncResult()
    class Loading(progress: Double? = null) : SyncResult(progress = progress)
    class Error(error: String? = null) : SyncResult(error = error)
}