package com.project.weatherAroundTheWorld.data.api

import com.project.weatherAroundTheWorld.data.response.CitiesDto
import com.project.weatherAroundTheWorld.data.response.DailyForecastDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET("locations/v1/topcities/{numberOfCities}")
   suspend fun fetchCitiesList(@Path("numberOfCities") numberOfCities: String,
                               @Query("apikey")apiKey:String): List<CitiesDto>

    @GET("forecasts/v1/daily/1day/{keyForCity}")
    suspend fun getForecast(
        @Path("keyForCity") keyForCity: String,
        @Query("apikey") apiKey: String
    ):DailyForecastDto
}