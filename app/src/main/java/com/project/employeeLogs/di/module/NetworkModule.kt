package com.project.employeeLogs.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.employeeLogs.BuildConfig
import com.project.employeeLogs.utils.ApiConstants
import com.project.employeeLogs.data.api.ApiService
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
    @Named(ApiConstants.BASE_URL)
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named(ApiConstants.BASE_URL_ANIME)
    fun provideRetrofitAnime(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiConstants.BASE_URL_ANIME)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Named(ApiConstants.BASE_URL)
    fun provideApiService(@Named(ApiConstants.BASE_URL) retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Named(ApiConstants.BASE_URL_ANIME)
    fun provideApiServiceAnime(@Named(ApiConstants.BASE_URL_ANIME) retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}