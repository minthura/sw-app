package com.minthuya.localdbkit.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.minthuya.localdbkit.entity.Setting

@Dao
interface SettingDao {
    @Query("SELECT * FROM settings WHERE `key` = :key LIMIT 1")
    suspend fun getSetting(key: String): Setting?

    @Insert
    suspend fun insert(setting: Setting): Long

    @Update
    suspend fun update(setting: Setting): Long

    @Transaction
    suspend fun setSetting(setting: Setting): Boolean {
        val rows = getSetting(setting.key)?.let {
            update(setting)
        } ?: insert(setting)
        return rows > 0
    }
}