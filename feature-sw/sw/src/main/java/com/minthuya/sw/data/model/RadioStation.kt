package com.minthuya.sw.data.model

import java.time.LocalTime

data class RadioStation(
    val id: Int = 0,
    val frequency: String,
    val name: String,
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val language: String,
    val location: String,
    val adm: String,
    val isLiveNow: Boolean
)
