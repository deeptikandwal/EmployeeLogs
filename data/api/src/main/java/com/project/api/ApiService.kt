package com.project.api

import com.project.response.CitiesDto
import com.project.response.DailyForecastDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET("locations/v1/topcities/{numberOfCities}")
   suspend fun fetchCitiesList(@Path("numberOfCities") numberOfCities: String,
                               @Query("apikey")apiKey:String): List<CitiesDto>

    @GET("currentconditions/v1/{keyForCity}")
    suspend fun getForecast(
        @Path("keyForCity") keyForCity: String,
        @Query("apikey") apiKey: String
    ):List<DailyForecastDto>
}