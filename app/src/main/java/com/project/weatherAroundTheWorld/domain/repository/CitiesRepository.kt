package com.project.weatherAroundTheWorld.domain.repository
import com.project.weatherAroundTheWorld.domain.model.CitiesDomainModel
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun fetchCitiesList(apiKey:String): Flow<List<CitiesDomainModel>>
}