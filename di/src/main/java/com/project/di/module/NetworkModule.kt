package com.project.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.weatherAroundTheWorld.utils.ApiConstants
import com.project.api.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class  NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    else OkHttpClient
        .Builder()
        .build()



    @Provides
    @Singleton
    @Named(ApiConstants.WEATHER_URL)
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiConstants.WEATHER_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Named(ApiConstants.WEATHER_URL)
    fun provideApiService(@Named(ApiConstants.WEATHER_URL) retrofit: Retrofit): com.project.api.ApiService =
        retrofit.create(com.project.api.ApiService::class.java)


}