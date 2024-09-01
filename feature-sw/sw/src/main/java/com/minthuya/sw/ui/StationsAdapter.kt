package com.minthuya.sw.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.minthuya.localdbkit.entity.Station
import com.minthuya.sw.R
import com.minthuya.sw.databinding.SwStationListItemBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

class StationsAdapter(
    private val onLoadMore: ((Int) -> Unit)
): RecyclerView.Adapter<StationsAdapter.StationViewHolder>() {

    var offset = 0

    val df: DateTimeFormatter = DateTimeFormatterBuilder().apply {
        parseCaseInsensitive()
        appendPattern("h:mm a")
    }.toFormatter(Locale.ENGLISH)

    private val stations = mutableListOf<Station>()

    inner class StationViewHolder(
        private val binding: SwStationListItemBinding
    ): ViewHolder(binding.root) {
        fun bind(station: Station) {
            binding.swTitle.text = station.name
            val from = convertUtcToLocalTime(station.startTime)
            val to = convertUtcToLocalTime(station.endTime)

            binding.swSubtitle.text = buildString {
                append(station.frequency)
                appendLine(" kHz")
                append("Live ")
                if (from == to) {
                    append("all day")
                } else {
                    append("from ${from.format(df)} to ${to.format(df)}")
                }
            }
            binding.statusView.background = ContextCompat.getDrawable(
                binding.root.context,
                if (isCurrentTimeBetween(from, to)) {
                    R.drawable.sw_online
                } else {
                    R.drawable.sw_offline
                }
            )
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

    fun clearStations() {
        offset = 0
        val size = stations.size
        stations.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addStations(data: List<Station>) {
        val size = stations.size
        stations.addAll(data)
        notifyItemRangeInserted(size, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding = SwStationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun getItemCount(): Int = stations.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stations[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(
            object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == stations.lastIndex) {
                            offset++
                            onLoadMore.invoke(offset)
                        }
                    }
                }
            }
        )
    }
}