package com.project.employeeLogs.domain.repository

import com.project.employeeLogs.domain.model.AnimeDomainModel
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnimeList(title: String?): Flow<List<AnimeDomainModel>>

}