package com.project.employeeLogs.di.module

import com.project.employeeLogs.data.mapper.AnimeMapper
import com.project.employeeLogs.data.mapper.EmployeeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MapToDomainModule {

    @Provides
    fun providerEmployeeMapper() = EmployeeMapper()

    @Provides
    fun providerAnimeMapper() = AnimeMapper()
}