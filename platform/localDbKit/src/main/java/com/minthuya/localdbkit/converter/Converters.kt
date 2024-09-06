package com.minthuya.localdbkit.converter

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

class Converters {
    val df = DateTimeFormatterBuilder().apply {
        parseCaseInsensitive()
        appendPattern("HH:mm")
    }.toFormatter(Locale.ENGLISH)

    @TypeConverter
    fun fromTimestamp(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, df) } ?: LocalTime.now()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalTime?): String? {
        return date?.toString()
    }
}