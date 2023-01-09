package com.project.di.module

import com.project.api.ApiService
import com.project.weatherAroundTheWorld.data.db.WeatherDb
import com.project.mapper.CitiesMapper
import com.project.mapper.DailyForecastMapper
import com.project.repository.CitiesRepositoryImpl
import com.project.repository.ForecastRepositoryImpl
import com.project.domain.repository.CitiesRepository
import com.project.domain.repository.ForecastRepository
import com.project.weatherAroundTheWorld.utils.ApiConstants
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
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
        apiService: com.project.api.ApiService,
    mapper: CitiesMapper,
    @Named("ioDispatcher")
        dispatcher: CoroutineDispatcher,
    connectionUtils: ConnectionUtils
    ): CitiesRepository =
        CitiesRepositoryImpl(weatherDb,apiService, dispatcher,mapper, connectionUtils)


}