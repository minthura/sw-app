package com.minthuya.localdbkit.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.minthuya.localdbkit.entity.Station

@Dao
interface StationDao {
    @Query(
        "SELECT * FROM stations WHERE language LIKE :language AND (:station IS NULL OR name = :station) LIMIT :pageSize OFFSET :pageIndex * :pageSize"
    )
    suspend fun getStations(pageSize: Int, pageIndex: Int, language: String, station: String?): List<Station>

    @Query("SELECT * FROM stations WHERE language LIKE :language")
    suspend fun findByLanguage(language: String): List<Station>

    @Query("SELECT DISTINCT language from stations")
    suspend fun getLanguages(): List<String>

    @Insert
    suspend fun insert(station: Station)

    @Delete
    suspend fun delete(station: Station)

    @Query("DELETE FROM stations")
    suspend fun deleteAll()

    @Query("SELECT COUNT(id) FROM stations")
    suspend fun stationsCount(): Int
}