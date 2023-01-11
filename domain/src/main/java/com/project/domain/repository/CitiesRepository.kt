package com.project.domain.repository
import com.project.domain.model.CitiesDomainModel
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun fetchCitiesList(apiKey:String): Flow<List<CitiesDomainModel>>
}