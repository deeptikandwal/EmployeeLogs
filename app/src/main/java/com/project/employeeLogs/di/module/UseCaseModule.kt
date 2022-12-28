package com.project.employeeLogs.di.module

import com.project.employeeLogs.domain.repository.AnimeRepository
import com.project.employeeLogs.domain.repository.EmployeeRepository
import com.project.employeeLogs.domain.usecase.GetAnimesUseCase
import com.project.employeeLogs.domain.usecase.GetEmployeesUseCase
import com.project.employeeLogs.utils.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCase( employeeRepository: EmployeeRepository) =
        GetEmployeesUseCase(employeeRepository)

    @Provides
    fun provideUseCaseAnime( animeRepository: AnimeRepository) = GetAnimesUseCase(animeRepository)

}