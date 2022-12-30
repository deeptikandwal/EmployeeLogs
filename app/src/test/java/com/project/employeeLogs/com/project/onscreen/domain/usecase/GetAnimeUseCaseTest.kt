package com.project.employeeLogs.com.project.onscreen.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.employeeLogs.domain.repository.AnimeRepository
import com.project.employeeLogs.domain.usecase.GetAnimesUseCase
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
    lateinit var getAnimesUseCase: GetAnimesUseCase

    @Mock
    lateinit var animeRepository: AnimeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAnimesUseCase = GetAnimesUseCase(animeRepository)
    }

    @Test
    fun getAnimesTest() = runTest {
        getAnimesUseCase.invoke("Naruto")
        Mockito.verify(animeRepository, Mockito.times(1)).getAnimeList("Naruto")
    }

}