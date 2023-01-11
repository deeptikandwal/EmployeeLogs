package com.project.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.db.entity.CitiesEntity

@Dao
interface CitiesDao {
    @Query("SELECT * FROM Cities")
     fun getAlLCities(): List<CitiesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CitiesEntity>)

    @Query("DELETE FROM Cities")
    suspend fun deleteAllCities()
}
