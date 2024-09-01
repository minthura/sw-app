package com.minthuya.localdbkit.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.minthuya.localdbkit.constant.TableName.TBL_STATIONS
import java.time.LocalTime

@Entity(tableName = TBL_STATIONS)
data class Station(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val frequency: String,
    val name: String,
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val language: String,
    val location: String,
    val adm: String,
)
