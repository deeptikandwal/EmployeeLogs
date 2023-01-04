package com.project.weatherAroundTheWorld.com.project.weatherAroundTheWorld.data.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.mapper.CitiesMapper
import com.project.response.CitiesDto
import com.project.response.Country
import com.project.response.Region

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CitiesMapperTest {

    private  lateinit var citiesDto: List<CitiesDto>
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mapper: CitiesMapper

    @Before
    fun setUp(){
        citiesDto= listOf(CitiesDto("Berlin","Germany","11234", Region("Europe"), Country("Germany"),),)
        mapper= CitiesMapper()
    }

    @Test
    fun mapCitiesToDomainTest(){
        Assert.assertEquals(mapper.mapCitiesToDomain(citiesDto).get(0).key,"11234")
        Assert.assertEquals(mapper.mapCitiesToDomain(citiesDto).get(0).city,"Berlin (Germany) ")
    }


}