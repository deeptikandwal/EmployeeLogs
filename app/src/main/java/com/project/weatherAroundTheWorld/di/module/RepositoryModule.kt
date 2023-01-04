package com.project.weatherAroundTheWorld.di.module

import com.project.weatherAroundTheWorld.data.api.ApiService
import com.project.weatherAroundTheWorld.data.db.WeatherDb
import com.project.weatherAroundTheWorld.data.mapper.CitiesMapper
import com.project.weatherAroundTheWorld.data.mapper.DailyForecastMapper
import com.project.weatherAroundTheWorld.data.repository.CitiesRepositoryImpl
import com.project.weatherAroundTheWorld.data.repository.ForecastRepositoryImpl
import com.project.weatherAroundTheWorld.domain.repository.CitiesRepository
import com.project.weatherAroundTheWorld.domain.repository.ForecastRepository
import com.project.weatherAroundTheWorld.utils.ApiConstants
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepositoryWeather(
        weatherDb: WeatherDb,
        @Named(ApiConstants.WEATHER_URL)
        apiService: ApiService,
        @Named("ioDispatcher")
        dispatcher: CoroutineDispatcher,
        mapper: DailyForecastMapper,
        connectionUtils: ConnectionUtils
    ): ForecastRepository =
        ForecastRepositoryImpl(weatherDb, apiService,  dispatcher, mapper,connectionUtils)

@Provides
    fun provideRepositoryWeatherCities(
    weatherDb: WeatherDb,
        @Named(ApiConstants.WEATHER_URL)
        apiService: ApiService,
        mapper: CitiesMapper,
        @Named("ioDispatcher")
        dispatcher: CoroutineDispatcher,
        connectionUtils: ConnectionUtils
    ): CitiesRepository =
        CitiesRepositoryImpl(weatherDb,apiService, dispatcher,mapper, connectionUtils)


}