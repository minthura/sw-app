package com.minthuya.sw.data.model

import java.io.File

sealed class UnzipState(directory: File? = null) {
    class Unzipping(val progress: Double) : UnzipState()
    class Finished(val dir: File) : UnzipState(dir)
}