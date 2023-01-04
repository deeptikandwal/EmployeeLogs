package com.project.weatherAroundTheWorld.di.module

import android.content.Context
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
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