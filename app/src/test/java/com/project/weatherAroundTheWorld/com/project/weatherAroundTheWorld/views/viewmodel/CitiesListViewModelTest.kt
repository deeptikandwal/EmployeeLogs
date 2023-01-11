package com.project.weatherAroundTheWorld.com.project.weatherAroundTheWorld.views.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.domain.model.CitiesDomainModel
import com.project.domain.usecase.GetCitiesListUseCase
import com.project.weatherAroundTheWorld.views.viewmodel.CitiesListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class CitiesListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    lateinit var citiesListViewModel: CitiesListViewModel

    @Mock
    lateinit var getCitiesListUseCase: GetCitiesListUseCase
    val apikey="ddddddddddddddddddddddd"


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        citiesListViewModel = CitiesListViewModel(getCitiesListUseCase)
        citiesListViewModel.getCitiesListUseCase = getCitiesListUseCase
    }

    @Test
    fun handleOperationSuccessTest(): Unit = runTest {
        Mockito.`when`(getCitiesListUseCase(apikey)).thenReturn(flowOf( listOf(
        CitiesDomainModel(
                "11234",
                "Europe(23.45,45.56)",
                "Berlin(Germany)"
            ),
        ))
        )
        val employeeList=getCitiesListUseCase(apikey).flatMapConcat { it.asFlow()}.toList()
        Assert.assertNotEquals(employeeList.size, 0)
        Assert.assertEquals(employeeList.get(0).key, "11234")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun handleOperationFailTest(): Unit = runTest {
        Mockito.`when`(getCitiesListUseCase(apikey)).thenThrow(
            HttpException(
                Response.error<Any>(
                    409,
                    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
                )
            )
        )
        Assert.assertEquals(getCitiesListUseCase(apikey).flatMapConcat { it.asFlow()}.toList().size, 0)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}