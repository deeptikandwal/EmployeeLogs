package com.project.db.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.db.dao.ForecastDao
import com.project.db.dao.CitiesDao
import com.project.db.entity.CitiesEntity
import com.project.db.entity.DailyForecastEntity


@Database(entities = [DailyForecastEntity::class, CitiesEntity::class], version = 1, exportSchema = false)
abstract class WeatherDb: RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
    abstract fun foreCastDao(): ForecastDao
}