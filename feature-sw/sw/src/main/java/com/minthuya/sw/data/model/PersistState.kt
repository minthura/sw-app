package com.minthuya.sw.data.model

sealed class PersistState(val progress: Double = 0.0) {
    data object Success : PersistState()
    data object ReadingFile : PersistState()
    class Persisting(progress: Double) : PersistState(progress = progress)
}