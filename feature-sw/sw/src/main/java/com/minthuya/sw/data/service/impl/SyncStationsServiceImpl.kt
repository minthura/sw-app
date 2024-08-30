package com.minthuya.sw.data.service.impl

import android.content.Context
import com.minthuya.localdbkit.LocalDbKit
import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.data.service.SyncStationsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.util.Locale

class SyncStationsServiceImpl(
    private val context: Context,
    private val localDbKit: LocalDbKit,
    private val scope: CoroutineDispatcher = Dispatchers.Default,
): SyncStationsService {

    private val df = DateTimeFormatterBuilder().apply {
        parseCaseInsensitive()
        appendPattern("HHmm")
    }.toFormatter(Locale.ENGLISH)

    override fun read(): Flow<Double> =
        flow {
            val db = localDbKit.getDb()
            if (db.stationDao().stationsCount() > 0) {
                emit(100.0)
                return@flow
            }
            context.assets.open("nxa24.xlsx").use {
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
                        emit(percent)
                    }
                }
            }
            emit(100.0)
        }.flowOn(scope)
}