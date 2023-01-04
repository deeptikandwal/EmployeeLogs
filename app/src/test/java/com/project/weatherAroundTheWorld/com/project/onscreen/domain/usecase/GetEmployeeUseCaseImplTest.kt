package com.project.weatherAroundTheWorld.com.project.onscreen.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)

class GetEmployeeUseCaseImplTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var getEmployeesUseCaseImpl: GetEmployeesUseCase
    @Mock
    lateinit var onScreenRepository: EmployeeRepository
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        getEmployeesUseCaseImpl= GetEmployeesUseCase(onScreenRepository)
    }

    @Test
    fun `get employees`()= runTest{
        getEmployeesUseCaseImpl.invoke()
        Mockito.verify(onScreenRepository,Mockito.times(1)).getEmployees()
    }

}