package com.project.employeeLogs.data.mapper

import com.project.employeeLogs.data.db.entity.AnimeEntity
import com.project.employeeLogs.data.response.AnimeDto
import com.project.employeeLogs.domain.model.AnimeDomainModel

class AnimeMapper {
    fun mapToAnimeDomainFromDto(itemsdto: List<AnimeDto>): List<AnimeDomainModel> =
        itemsdto.map { animeDto ->
            AnimeDomainModel(
                animeDto.id ?: 0,
                animeDto.anime.orEmpty().plus("-").plus(animeDto.character.orEmpty()),
                animeDto.quote.orEmpty()
            )
        }

    fun mapToAnimeEntity(itemsdto: List<AnimeDto>): List<AnimeEntity> = itemsdto.map { animeDto ->
        AnimeEntity(animeDto.id, animeDto.anime, animeDto.character, animeDto.quote)
    }

    fun mapToAnimeDomainFromEntity(itemsEntity: List<AnimeEntity>): List<AnimeDomainModel> =
        itemsEntity.map { animeEntity ->
            AnimeDomainModel(
                animeEntity.id ?: 0,
                animeEntity.anime.orEmpty().plus("-").plus(animeEntity.character.orEmpty()),
                animeEntity.quote.orEmpty()
            )
        }
}