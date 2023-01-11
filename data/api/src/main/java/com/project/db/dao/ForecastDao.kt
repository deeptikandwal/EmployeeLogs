package com.project.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.db.entity.DailyForecastEntity

@Dao
interface ForecastDao {
    @Query("SELECT * FROM Forecast")
    fun getForecast(): List<DailyForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(foreCast: List<DailyForecastEntity>)

    @Query("SELECT * FROM Forecast where keyForCity = :cityKey ORDER BY id DESC")
    fun getForeCastForCity(cityKey: String): DailyForecastEntity

    @Query("DELETE FROM Forecast")
    suspend fun deleteForecasts()
}