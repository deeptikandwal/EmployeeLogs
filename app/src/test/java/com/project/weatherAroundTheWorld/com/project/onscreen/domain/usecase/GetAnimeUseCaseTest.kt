package com.project.weatherAroundTheWorld.com.project.onscreen.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.weatherAroundTheWorld.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)

class GetAnimeUseCaseTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var getAnimesUseCase: GetForecastUseCase

    @Mock
    lateinit var animeRepository: AnimeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAnimesUseCase = GetForecastUseCase(animeRepository)
    }

    @Test
    fun getAnimesTest() = runTest {
        getAnimesUseCase.invoke("Naruto")
        Mockito.verify(animeRepository, Mockito.times(1)).getAnimeList("Naruto")
    }

}