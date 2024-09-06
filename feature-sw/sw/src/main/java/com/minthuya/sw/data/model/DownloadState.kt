package com.minthuya.sw.data.model

import java.io.File

sealed class DownloadState {
    class Downloading(val progress: Double) : DownloadState()
    class Finished(val file: File) : DownloadState()
}