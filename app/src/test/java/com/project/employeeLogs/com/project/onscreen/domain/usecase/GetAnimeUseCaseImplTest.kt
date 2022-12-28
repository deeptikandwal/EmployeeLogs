package com.project.employeeLogs.com.project.onscreen.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.employeeLogs.domain.repository.EmployeeRepository
import com.project.employeeLogs.domain.usecase.GetAnimesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)

class GetAnimeUseCaseImplTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var getAnimesUseCaseImpl: GetAnimesUseCase

    @Mock
    lateinit var onScreenRepository: EmployeeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAnimesUseCaseImpl = GetAnimesUseCase(onScreenRepository)
    }

    @Test
    fun getAnimesTest() = runTest {
        getAnimesUseCaseImpl.getAnimes("Naruto")
        Mockito.verify(onScreenRepository, Mockito.times(1)).getAnimeList("Naruto")
    }

}