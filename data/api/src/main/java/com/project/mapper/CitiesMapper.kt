package com.project.mapper

import com.project.domain.model.CitiesDomainModel
import com.project.response.CitiesDto

class CitiesMapper {
    fun mapCitiesToDomain(citiesDto: List<CitiesDto>):List<CitiesDomainModel>{
       return citiesDto.map { dto ->
            CitiesDomainModel(0,
                dto.key,
                dto.region?.englishName.orEmpty().plus(" ").plus(dto.geoPosition?.latitude).plus(",").plus(
                    dto.geoPosition?.longitude),
                dto.localizedName.orEmpty().plus(" (")
                    .plus(dto.country?.englishName.orEmpty()).plus(") ")
            )
        }.toList()
    }
}