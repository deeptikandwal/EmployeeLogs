package com.project.mapper

import com.project.db.entity.CitiesEntity
import com.project.domain.model.CitiesDomainModel
import com.project.response.CitiesDto
import javax.inject.Inject

class CitiesMapper @Inject constructor() {
    fun mapCitiesToDomain(citiesDto: List<CitiesDto>):List<CitiesEntity>{
        return citiesDto.map { dto ->
            CitiesEntity(0,
                dto.key,
                dto.region?.englishName.orEmpty().plus(" ").plus(dto.geoPosition?.latitude).plus(",").plus(
                    dto.geoPosition?.longitude),
                dto.localizedName.orEmpty().plus(" (")
                    .plus(dto.country?.englishName.orEmpty()).plus(") ")
            )
        }
    }

}