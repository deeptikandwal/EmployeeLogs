package com.project.weatherAroundTheWorld.com.project.onscreen.data.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.weatherAroundTheWorld.data.db.entity.AnimeEntity
import com.project.weatherAroundTheWorld.data.mapper.DailyForecastMapper
import com.project.weatherAroundTheWorld.data.response.DailyForecastDto
import com.project.weatherAroundTheWorld.data.response.Metric
import com.project.weatherAroundTheWorld.data.response.Temperature
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ForecastMapperTest {
    private lateinit var dailyForecastDtos: List<DailyForecastDto>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mapper: DailyForecastMapper

    @Before
    fun setUp(){
        dailyForecastDtos= listOf(
            DailyForecastDto("2023-01-05T01:33:00+09:00", Temperature(Metric(7.9,"C")),"Hazy Cloud",false,isDayTime = false),
        )

        mapper= DailyForecastMapper()
    }

       @Test
    fun mapToAnimeDomainTest(){
        Assert.assertEquals(mapper.mapToForecastDomain(dailyForecastDtos).get(0).hasprecipitation,false)
    }


}