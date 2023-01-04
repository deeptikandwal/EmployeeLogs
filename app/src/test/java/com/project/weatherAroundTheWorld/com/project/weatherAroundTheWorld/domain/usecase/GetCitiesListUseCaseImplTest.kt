package com.project.weatherAroundTheWorld.com.project.weatherAroundTheWorld.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.domain.repository.CitiesRepository
import com.project.domain.usecase.GetCitiesListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)

class GetCitiesListUseCaseTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var getCitiesListUseCase: GetCitiesListUseCase
    @Mock
    lateinit var citiesRepository: CitiesRepository
    val apikey="ddddddddddddddddddddddd"

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        getCitiesListUseCase= GetCitiesListUseCase(citiesRepository)
    }

    @Test
    fun `get employees`()= runTest{
        getCitiesListUseCase.invoke(apikey)
        Mockito.verify(citiesRepository,Mockito.times(1)).fetchCitiesList(apikey)
    }

}