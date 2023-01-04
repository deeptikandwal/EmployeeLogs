package com.project.domain.usecase
import com.project.domain.model.DailyForecastDomainModel
import com.project.domain.repository.ForecastRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val forecastRepository: ForecastRepository)  {
     operator fun invoke(keyForCity: String,apiKey:String): Flow<List<DailyForecastDomainModel>> {
       return forecastRepository.getForecasts(keyForCity,apiKey)
    }
}