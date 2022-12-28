package com.project.employeeLogs.di.module

import android.app.Application
import androidx.room.Room
import com.project.employeeLogs.data.db.EmployeeDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): EmployeeDb =
        Room.databaseBuilder(app.applicationContext, EmployeeDb::class.java, "employee_database").allowMainThreadQueries()
            .build()
}