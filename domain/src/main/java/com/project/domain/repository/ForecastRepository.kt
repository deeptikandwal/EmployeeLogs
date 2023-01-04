package com.project.domain.repository
import com.project.domain.model.DailyForecastDomainModel
import kotlinx.coroutines.flow.Flow

interface ForecastRepository{
    fun getForecasts(keyForCity:String,apiKey:String): Flow<List<DailyForecastDomainModel>>
}