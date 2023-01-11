package com.project.domain.usecase

import com.project.domain.model.CitiesDomainModel
import com.project.domain.repository.CitiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesListUseCase @Inject constructor(val citiesRepository: CitiesRepository) {

    operator fun invoke(apiKey:String):Flow<List<CitiesDomainModel>>{
        return citiesRepository.fetchCitiesList(apiKey)
    }

}