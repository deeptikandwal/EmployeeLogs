package com.project.employeeLogs.di.module

import android.content.Context
import com.project.employeeLogs.utils.ConnectionUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Provides
    fun provideConnectionUtils(@ApplicationContext context: Context)= ConnectionUtils(context)
}