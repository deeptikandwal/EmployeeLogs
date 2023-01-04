package com.project.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.domain.model.CitiesDomainModel

@Dao
interface CitiesDao {
    @Query("SELECT * FROM Cities")
     fun getAlLCities(): List<CitiesDomainModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CitiesDomainModel>)

    @Query("DELETE FROM Cities")
    suspend fun deleteAllCities()
}
