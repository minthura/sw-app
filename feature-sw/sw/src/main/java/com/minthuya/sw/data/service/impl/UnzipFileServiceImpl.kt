package com.minthuya.sw.data.service.impl

import android.content.Context
import com.minthuya.sw.data.model.UnzipState
import com.minthuya.sw.data.service.UnzipFileService
import com.minthuya.sw.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class UnzipFileServiceImpl(
    private val context: Context,
    private val scope: CoroutineDispatcher = Dispatchers.IO,
) : UnzipFileService {
    override operator fun invoke(zipFile: File): Flow<UnzipState> = flow {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

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
                            val progress = (extractedSize * Constants.INT_100 / totalSize)
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