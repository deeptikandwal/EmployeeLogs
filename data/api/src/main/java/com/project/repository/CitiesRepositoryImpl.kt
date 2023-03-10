package com.project.repository
import com.project.api.ApiService
import com.project.db.database.WeatherDb
import com.project.db.entity.CitiesEntity
import com.project.domain.model.CitiesDomainModel
import com.project.domain.repository.CitiesRepository
import com.project.mapper.CitiesMapper
import com.project.response.CitiesDto
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Named

class CitiesRepositoryImpl(
    val weatherDb: WeatherDb,
    val apiService: ApiService,
    @Named("ioDispatcher")
    val dispatcher: CoroutineDispatcher,
    val mapper: CitiesMapper,
    val connectionUtils: ConnectionUtils
) : CitiesRepository {
    val dao = weatherDb.citiesDao()
    override fun fetchCitiesList(apiKey: String): Flow<List<CitiesDomainModel>> =
        flow {
            if (connectionUtils.isNetworkAvailable()) {
                apiService.fetchCitiesList("150", apiKey).also { list ->
                    emit(getCitiesDomainList(list).map { getDomainModel(it) })
                    dao.insertCities(getCitiesDomainList(list))
                }
            } else {
                emit(dao.getAlLCities().map {getDomainModel(it)})
            }
        }.flowOn(dispatcher)

    private fun getDomainModel(it: CitiesEntity) = CitiesDomainModel(it.key, it.region, it.city)

    private fun getCitiesDomainList(list: List<CitiesDto>) = mapper.mapCitiesToEntity(list)

}