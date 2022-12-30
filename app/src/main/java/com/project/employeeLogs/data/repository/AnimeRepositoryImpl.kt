package com.project.employeeLogs.data.repository

import com.project.employeeLogs.data.api.ApiService
import com.project.employeeLogs.data.db.EmployeeDb
import com.project.employeeLogs.data.mapper.AnimeMapper
import com.project.employeeLogs.domain.model.AnimeDomainModel
import com.project.employeeLogs.domain.repository.AnimeRepository
import com.project.employeeLogs.utils.ConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    employeeDb: EmployeeDb,
    val apiService: ApiService,
    val mapper: AnimeMapper,
    val dispatcher: CoroutineDispatcher,
    val connectionUtils: ConnectionUtils
) : AnimeRepository {
    var animeDao = employeeDb.animesDao()

    override fun getAnimeList(title: String?)= flow {
            if(connectionUtils.isNetworkAvailable()){
                emitAnime(title)
            }else{
                getDomainAnimeFromEntity(title)
            }
        }.flowOn(dispatcher)

    private suspend fun FlowCollector<List<AnimeDomainModel>>.getDomainAnimeFromEntity(
        title: String?
    ) {
        if (!animeDao.getAllAnimes().isEmpty() && animeDao.getAnimeByName(title) != null) {
            emit(mapper.mapToAnimeDomainFromEntity(animeDao.getAllAnimes()))
        }
    }

    private suspend fun FlowCollector<List<AnimeDomainModel>>.emitAnime(
        title: String?
    ) {
        val result = apiService.fetchAnimeUsingTitle(title)
        animeDao.apply {
            deleteAllAnimes()
            insertAnimes(mapper.mapToAnimeEntity(result))

        }
        emit(mapper.mapToAnimeDomainFromDto(result))
    }


}