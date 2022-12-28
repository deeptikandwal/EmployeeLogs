package com.project.employeeLogs.domain.usecase
import com.project.employeeLogs.domain.model.AnimeDomainModel
import com.project.employeeLogs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetAnimesUseCase(private val animeRepository: AnimeRepository)  {
     operator fun invoke(title: String?): Flow<List<AnimeDomainModel>> {
       return animeRepository.getAnimeList(title)
    }
}