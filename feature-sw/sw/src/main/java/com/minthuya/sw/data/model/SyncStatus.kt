package com.minthuya.sw.data.model

import com.minthuya.networkkit.UiResult

sealed class SyncStatus<T>(val body: T? = null) {
    class Success<T>(body: T) : SyncStatus<T>(body)
    class Loading<T> : SyncStatus<T>()
}