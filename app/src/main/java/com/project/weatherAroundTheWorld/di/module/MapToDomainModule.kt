package com.project.weatherAroundTheWorld.di.module

import com.project.weatherAroundTheWorld.data.mapper.CitiesMapper
import com.project.weatherAroundTheWorld.data.mapper.DailyForecastMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MapToDomainModule {

    @Provides
    fun providerForecastMapper() = DailyForecastMapper()

    @Provides
    fun providerCitiesMapper() = CitiesMapper()
}