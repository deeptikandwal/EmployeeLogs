package com.project.weatherAroundTheWorld.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel

@Dao
interface  ForecastDao {
    @Query("SELECT * FROM Forecast")
    fun getForecast():DailyForecastDomainModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(foreCast:DailyForecastDomainModel)

    @Query("DELETE FROM Forecast")
    suspend fun deleteForecast()
}