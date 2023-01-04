package com.project.weatherAroundTheWorld.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.db.dao.ForecastDao
import com.project.db.dao.CitiesDao
import com.project.domain.model.CitiesDomainModel
import com.project.domain.model.DailyForecastDomainModel

@Database(entities = [DailyForecastDomainModel::class, CitiesDomainModel::class], version = 1, exportSchema = false)
abstract class WeatherDb: RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
    abstract fun foreCastDao(): ForecastDao
}