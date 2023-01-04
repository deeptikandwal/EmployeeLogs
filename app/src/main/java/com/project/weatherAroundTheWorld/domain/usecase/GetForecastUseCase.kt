package com.project.weatherAroundTheWorld.domain.usecase
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel
import com.project.weatherAroundTheWorld.domain.repository.ForecastRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val forecastRepository: ForecastRepository)  {
     operator fun invoke(keyForCity: String,apiKey:String): Flow<List<DailyForecastDomainModel>> {
       return forecastRepository.getForecasts(keyForCity,apiKey)
    }
}