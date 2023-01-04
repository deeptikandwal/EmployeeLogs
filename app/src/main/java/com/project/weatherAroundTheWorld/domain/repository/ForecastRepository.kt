package com.project.weatherAroundTheWorld.domain.repository
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel
import kotlinx.coroutines.flow.Flow

interface ForecastRepository{
    fun getForecasts(keyForCity:String,apiKey:String): Flow<List<DailyForecastDomainModel>>
}