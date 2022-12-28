package com.project.employeeLogs.di.module

import com.project.employeeLogs.data.api.ApiService
import com.project.employeeLogs.data.db.EmployeeDb
import com.project.employeeLogs.data.mapper.AnimeMapper
import com.project.employeeLogs.data.mapper.EmployeeMapper
import com.project.employeeLogs.data.repository.AnimeRepositoryImpl
import com.project.employeeLogs.data.repository.EmployeeRepositoryImpl
import com.project.employeeLogs.domain.repository.AnimeRepository
import com.project.employeeLogs.domain.repository.EmployeeRepository
import com.project.employeeLogs.utils.ApiConstants
import com.project.employeeLogs.utils.ConnectionUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(
        employeeDB: EmployeeDb,
        @Named(ApiConstants.BASE_URL)
        apiService: ApiService,
        mapper: EmployeeMapper,
        @Named("ioDispatcher")
        dispatcher: CoroutineDispatcher,
        connectionUtils: ConnectionUtils
    ): EmployeeRepository =
        EmployeeRepositoryImpl(employeeDB, apiService, mapper, dispatcher, connectionUtils)

    @Provides
    fun provideRepositoryAnime(
        employeeDB: EmployeeDb,
        @Named(ApiConstants.BASE_URL_ANIME)
        apiService: ApiService,
        mapper: AnimeMapper,
        @Named("ioDispatcher")
        dispatcher: CoroutineDispatcher,
        connectionUtils: ConnectionUtils
    ): AnimeRepository =
        AnimeRepositoryImpl(employeeDB, apiService, mapper, dispatcher, connectionUtils)


}