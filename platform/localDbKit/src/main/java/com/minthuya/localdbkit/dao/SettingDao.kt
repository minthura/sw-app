package com.minthuya.localdbkit.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.minthuya.localdbkit.entity.Setting

@Dao
interface SettingDao {
    @Query("SELECT * FROM settings WHERE `key` = :key LIMIT 1")
    suspend fun getSetting(key: String): Setting?

    @Query("SELECT * FROM settings")
    suspend fun getSettings(): List<Setting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: Setting): Long

    @Update
    suspend fun update(setting: Setting): Int

    @Transaction
    suspend fun setSettings(settings: List<Setting>): Boolean {
        var i = 0
        settings.forEach { setting ->
            i += (
                getSetting(setting.key)?.let {
                    update(it.copy(value = setting.value))
                } ?: insert(setting)
                ).toInt()
        }
        return i == settings.size
    }
}