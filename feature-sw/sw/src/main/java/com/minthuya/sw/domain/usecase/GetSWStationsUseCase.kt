package com.minthuya.sw.domain.usecase

import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.data.model.RadioStation
import com.minthuya.sw.data.repository.SWRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

interface GetSWStationsUseCase {
    operator fun invoke(offset: Int, limit: Int = 30, language: String, station: String?, isLiveNow: Boolean): Flow<List<RadioStation>>
}

class GetSWStationsUseCaseImpl(
    private val swRepository: SWRepository
): GetSWStationsUseCase {
    override fun invoke(
        offset: Int,
        limit: Int,
        language: String,
        station: String?,
        isLiveNow: Boolean
    ): Flow<List<RadioStation>> =
        swRepository.getStations(offset, limit, language, station).map { stations ->
            val filtered = stations.map {
                RadioStation(
                    id = it.id,
                    frequency = it.frequency,
                    name = it.name,
                    startTime = convertUtcToLocalTime(it.startTime),
                    endTime = convertUtcToLocalTime(it.endTime),
                    language = it.language,
                    location = it.location,
                    adm = it.adm,
                    isLiveNow = isCurrentTimeBetween(
                        convertUtcToLocalTime(it.startTime),
                        convertUtcToLocalTime(it.endTime),
                    )
                )
            }
            if (isLiveNow) {
                return@map filtered.filter {
                    it.isLiveNow
                }
            }
            filtered
        }

    private fun isCurrentTimeBetween(startTime: LocalTime, endTime: LocalTime): Boolean {
        val currentTime = LocalTime.now()

        return if (startTime.isBefore(endTime)) {
            // Normal range, e.g., 09:00 - 17:00
            currentTime.isAfter(startTime) && currentTime.isBefore(endTime)
        } else {
            // Overlapping range, e.g., 22:00 - 06:00 (spans midnight)
            currentTime.isAfter(startTime) || currentTime.isBefore(endTime)
        }
    }

    private fun convertUtcToLocalTime(utcTime: LocalTime): LocalTime {
        // Assume a fixed date since LocalTime only represents time, not date.
        val fixedDate = LocalDate.now()  // You can also use any specific date if needed.

        // Create a LocalDateTime by combining the fixed date and utcTime.
        val utcDateTime = LocalDateTime.of(fixedDate, utcTime)

        // Convert LocalDateTime to ZonedDateTime in UTC time zone.
        val utcZonedDateTime = utcDateTime.atZone(ZoneId.of("UTC"))

        // Convert the ZonedDateTime from UTC to the system default time zone.
        val localZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault())

        // Extract the LocalTime part from the local ZonedDateTime.
        return localZonedDateTime.toLocalTime()
    }

}