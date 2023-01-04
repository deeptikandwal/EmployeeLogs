package com.project.domain.repository
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun fetchCitiesList(apiKey:String): Flow<List<com.project.domain.model.CitiesDomainModel>>
}