package com.project.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.domain.repository.ForecastRepository
import com.project.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)

class GetForecastUseCaseTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var getForecastUseCase: GetForecastUseCase

    @Mock
    lateinit var forecastRepository: ForecastRepository
    val apikey="ddddddddddddddddddddddd"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getForecastUseCase = GetForecastUseCase(forecastRepository)
    }

    @Test
    fun getAnimesTest() = runBlockingTest {
        getForecastUseCase.invoke("11234",apikey)
        Mockito.verify(forecastRepository, Mockito.times(1)).getForecasts("11234",apikey)
    }

}