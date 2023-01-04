package com.project.weatherAroundTheWorld.di.module

import com.project.weatherAroundTheWorld.domain.repository.CitiesRepository
import com.project.weatherAroundTheWorld.domain.repository.ForecastRepository
import com.project.weatherAroundTheWorld.domain.usecase.GetForecastUseCase
import com.project.weatherAroundTheWorld.domain.usecase.GetCitiesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideUseCaseWeather( forecastRepository: ForecastRepository) = GetForecastUseCase(forecastRepository)

    @Provides
    fun provideUseCaseCities(citiesRepository: CitiesRepository) = GetCitiesListUseCase(citiesRepository)

}