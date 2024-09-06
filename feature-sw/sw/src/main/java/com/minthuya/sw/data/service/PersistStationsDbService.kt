package com.minthuya.sw.data.service

import com.minthuya.localdbkit.LocalDbKit
import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.data.model.PersistState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.InputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.util.Locale

interface PersistStationsDbService {
    operator fun invoke(inputStream: InputStream): Flow<PersistState>
}

class PersistStationsDbServiceImpl(
    private val localDbKit: LocalDbKit,
    private val scope: CoroutineDispatcher = Dispatchers.IO
): PersistStationsDbService {

    private val df = DateTimeFormatterBuilder().apply {
        parseCaseInsensitive()
        appendPattern("HHmm")
    }.toFormatter(Locale.ENGLISH)

    override operator fun invoke(
        inputStream: InputStream
    ): Flow<PersistState> = flow {
        println("PersistStationsDbService")
        emit(PersistState.ReadingFile)
        val db = localDbKit.getDb()
        inputStream.use {
            XSSFWorkbook(it).use { workbook ->
                db.stationDao().deleteAll()
                val sheet = workbook.getSheetAt(0)
                val total = sheet.count() - 2
                sheet.filterIndexed { index, row ->
                    index > 2 && row.getCell(0)?.numericCellValue.toString().isNotEmpty()
                }.mapIndexed { index, row ->
                    db.stationDao().insert(
                        Station(
                            frequency = row.getCell(0)?.numericCellValue.toString(),
                            name = row.getCell(2)?.stringCellValue.orEmpty(),
                            startTime = row.getCell(3)?.stringCellValue?.split("-")?.getOrNull(0)?.let {
                                try {
                                    LocalTime.parse(it.take(4), df)
                                } catch (e: DateTimeParseException) {
                                    LocalTime.now()
                                }
                            } ?: LocalTime.now(),
                            endTime = row.getCell(3)?.stringCellValue?.split("-")?.getOrNull(1)?.let {
                                try {
                                    LocalTime.parse(it.take(4), df)
                                } catch (e: DateTimeParseException) {
                                    LocalTime.now()
                                }
                            } ?: LocalTime.now(),
                            language = row.getCell(5)?.stringCellValue.orEmpty(),
                            location = row.getCell(8)?.stringCellValue.orEmpty(),
                            adm = row.getCell(9)?.stringCellValue.orEmpty(),
                        )
                    )
                    val percent = (index.inc().toDouble() / total.toDouble()) * 100
                    emit(PersistState.Persisting(percent))
                }
            }
            emit(PersistState.Success)
        }
    }.flowOn(scope)
}