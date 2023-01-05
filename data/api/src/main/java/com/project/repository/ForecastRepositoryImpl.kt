package com.project.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.project.domain.model.DailyForecastDomainModel
import com.project.domain.repository.ForecastRepository
import com.project.mapper.DailyForecastMapper
import com.project.response.DailyForecastDto
import com.project.weatherAroundTheWorld.data.db.WeatherDb
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Named

class ForecastRepositoryImpl(
    val weatherDb: WeatherDb,
    val apiService: com.project.api.ApiService,
    @Named("ioDispatcher")
    val dispatcher: CoroutineDispatcher,
    val mapper: DailyForecastMapper,
    val connectionUtils: ConnectionUtils
) : ForecastRepository {
    val dao = weatherDb.foreCastDao()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getForecasts(
        keyForCity: String,
        apiKey: String
    ): Flow<List<DailyForecastDomainModel>> =
        flow {
            if (connectionUtils.isNetworkAvailable()) {
                apiService.getForecast(keyForCity, apiKey).also { dto ->
                    emit(dailyForecastDomainModel(dto, keyForCity))
                    with(dao) {
                        insertForecasts(dailyForecastDomainModel(dto, keyForCity))
                    }
                }
            } else {
                if (dao.getForeCastForCity(keyForCity).keyForCity.equals(keyForCity))
                    emit(listOf(dao.getForeCastForCity(keyForCity)))
            }
        }.flowOn(dispatcher)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dailyForecastDomainModel(dto: List<DailyForecastDto>, keyForCity: String) =
        mapper.mapToForecastDomain(dto, keyForCity)
}