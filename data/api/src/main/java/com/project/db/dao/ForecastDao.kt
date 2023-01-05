package com.project.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.domain.model.DailyForecastDomainModel

@Dao
interface ForecastDao {
    @Query("SELECT * FROM Forecast")
    fun getForecast(): List<DailyForecastDomainModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(foreCast: List<DailyForecastDomainModel>)

    @Query("SELECT * FROM Forecast where keyForCity = :cityKey")
    fun getForeCastForCity(cityKey: String): DailyForecastDomainModel

    @Query("DELETE FROM Forecast")
    suspend fun deleteForecasts()
}