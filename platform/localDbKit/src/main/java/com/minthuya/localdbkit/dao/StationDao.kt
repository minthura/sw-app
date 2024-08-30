package com.minthuya.localdbkit.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.minthuya.localdbkit.entity.Station

@Dao
interface StationDao {
    @Query("SELECT * FROM stations")
    fun getAll(): List<Station>

    @Query("SELECT * FROM stations WHERE language LIKE :language")
    fun findByLanguage(language: String): List<Station>

    @Insert
    fun insert(station: Station)

    @Delete
    fun delete(station: Station)

    @Query("DELETE FROM stations")
    fun deleteAll()

    @Query("SELECT COUNT(id) FROM stations")
    fun stationsCount(): Int
}