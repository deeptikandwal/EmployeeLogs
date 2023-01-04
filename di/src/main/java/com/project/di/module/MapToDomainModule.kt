package com.project.di.module

import com.project.mapper.CitiesMapper
import com.project.mapper.DailyForecastMapper
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