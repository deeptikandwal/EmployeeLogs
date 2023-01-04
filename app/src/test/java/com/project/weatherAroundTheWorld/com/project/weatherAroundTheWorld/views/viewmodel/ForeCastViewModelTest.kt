package com.project.weatherAroundTheWorld.com.project.weatherAroundTheWorld.views.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.domain.model.DailyForecastDomainModel
import com.project.domain.usecase.GetForecastUseCase
import com.project.weatherAroundTheWorld.views.viewmodel.ForeCastViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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

@OptIn(ExperimentalCoroutinesApi::class)
class ForeCastViewModelTest {
    @Mock
    private lateinit var getForecastUseCase: GetForecastUseCase
    val apikey="ddddddddddddddddddddddd"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    private lateinit var foreCastViewModel: ForeCastViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        foreCastViewModel = ForeCastViewModel(getForecastUseCase)
        foreCastViewModel.forecastUseCase = getForecastUseCase
    }

    @Test
    fun handleOperationSuccessTest(): Unit = runTest(dispatcher) {
        Mockito.`when`(getForecastUseCase.invoke("11234",apikey)).thenReturn(flowOf( listOf(
            DailyForecastDomainModel(1,"Hazy Cloud" ,"7C" ,"",true,false),
        )))

        val animeList=getForecastUseCase.invoke("11234",apikey).flatMapConcat { it.asFlow()}.toList()
        Assert.assertNotEquals(animeList.size, 0)
        Assert.assertEquals(animeList.get(0).isDayTime, true)
    }

    @Test(expected = retrofit2.HttpException::class)
    fun handleOperationFailTest(): Unit = runTest(dispatcher) {
        Mockito.`when`((getForecastUseCase).invoke("11234",apikey)).thenThrow(
            HttpException(
                Response.error<Any>(
                    409,
                    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
                )
            )
        )
        Assert.assertEquals(getForecastUseCase.invoke("11234",apikey).flatMapConcat { it.asFlow()}.toList().size, 0)

    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}