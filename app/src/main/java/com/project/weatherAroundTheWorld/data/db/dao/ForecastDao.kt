package com.project.weatherAroundTheWorld.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel

@Dao
interface  ForecastDao {
    @Query("SELECT * FROM Forecast")
    fun getForecast():List<DailyForecastDomainModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(foreCast:List<DailyForecastDomainModel>)

    @Query("DELETE FROM Forecast")
    suspend fun deleteForecasts()
}