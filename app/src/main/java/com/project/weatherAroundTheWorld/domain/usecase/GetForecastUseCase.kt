package com.project.weatherAroundTheWorld.domain.usecase
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel
import com.project.weatherAroundTheWorld.domain.repository.ForecastRepository
import kotlinx.coroutines.flow.Flow

class GetForecastUseCase(private val forecastRepository: ForecastRepository)  {
     operator fun invoke(keyForCity: String,apiKey:String): Flow<DailyForecastDomainModel> {
       return forecastRepository.getForecasts(keyForCity,apiKey)
    }
}