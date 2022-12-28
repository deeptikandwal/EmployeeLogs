package com.project.employeeLogs.data.api

import com.project.employeeLogs.data.response.AnimeDto
import com.project.employeeLogs.data.response.EmployeeListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
   @GET("users")
   suspend fun fetchEmployees(): List<EmployeeListDto>

   @GET("api/quotes/anime")
   suspend fun fetchAnimeUsingTitle( @Query("title") title: String?):List<AnimeDto>

}