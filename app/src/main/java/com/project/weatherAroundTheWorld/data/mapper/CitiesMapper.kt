package com.project.weatherAroundTheWorld.data.mapper

import com.project.weatherAroundTheWorld.data.response.CitiesDto
import com.project.weatherAroundTheWorld.domain.model.CitiesDomainModel

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