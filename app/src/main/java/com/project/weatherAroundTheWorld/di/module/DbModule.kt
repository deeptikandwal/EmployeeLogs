package com.project.weatherAroundTheWorld.di.module

import android.app.Application
import androidx.room.Room
import com.project.weatherAroundTheWorld.data.db.WeatherDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): WeatherDb =
        Room.databaseBuilder(app.applicationContext, WeatherDb::class.java, "weather_database").allowMainThreadQueries()
            .build()
}