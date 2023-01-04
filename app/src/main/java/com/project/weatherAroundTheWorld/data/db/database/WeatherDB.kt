package com.project.weatherAroundTheWorld.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.weatherAroundTheWorld.data.db.dao.ForecastDao
import com.project.weatherAroundTheWorld.data.db.dao.CitiesDao
import com.project.weatherAroundTheWorld.data.db.entity.AnimeEntity
import com.project.weatherAroundTheWorld.domain.model.CitiesDomainModel
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel

@Database(entities = [DailyForecastDomainModel::class, CitiesDomainModel::class], version = 1, exportSchema = false)
abstract class WeatherDb: RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
    abstract fun foreCastDao(): ForecastDao
}