package com.minthuya.sw.data.service.impl

import android.content.Context
import com.minthuya.sw.data.model.DownloadState
import com.minthuya.sw.data.model.UnzipState
import com.minthuya.sw.data.service.UnzipFileService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class UnzipFileServiceImpl(
    private val context: Context,
    private val scope: CoroutineDispatcher = Dispatchers.IO,
): UnzipFileService {
    override fun unzipFile(zipFile: File): Flow<UnzipState> = flow {
        println("UnzipFileService")
        val buffer = ByteArray(1024)

        // Create destination directory if it doesn't exist
        val destDir = context.filesDir
        if (!destDir.exists()) {
            destDir.mkdirs()
        }

        // Calculate total size of the ZIP file
        val totalSize = zipFile.length()
        var extractedSize = 0.0

        ZipInputStream(FileInputStream(zipFile)).use { zis ->
            var zipEntry: ZipEntry? = zis.nextEntry
            while (zipEntry != null) {
                val newFile = File(destDir, zipEntry.name)
                if (zipEntry.isDirectory) {
                    // If the entry is a directory, create it
                    newFile.mkdirs()
                } else {
                    // If the entry is a file, extract it
                    newFile.parentFile?.mkdirs() // Ensure parent directories exist
                    FileOutputStream(newFile).use { fos ->
                        var len: Int
                        while (zis.read(buffer).also { len = it } > 0) {
                            fos.write(buffer, 0, len)
                            extractedSize += len
                            val progress = (extractedSize * 100 / totalSize)
                            emit(UnzipState.Unzipping(progress))
                        }
                    }
                }

                // Close current entry and move to next
                zipEntry = zis.nextEntry
            }
            zis.closeEntry()
            emit(UnzipState.Finished(destDir))
        }
    }.flowOn(scope)
}