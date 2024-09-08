package com.minthuya.localdbkit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.minthuya.localdbkit.converter.Converters
import com.minthuya.localdbkit.dao.SettingDao
import com.minthuya.localdbkit.dao.StationDao
import com.minthuya.localdbkit.entity.Setting
import com.minthuya.localdbkit.entity.Station

@Database(entities = [Station::class, Setting::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun settingsDao(): SettingDao
}