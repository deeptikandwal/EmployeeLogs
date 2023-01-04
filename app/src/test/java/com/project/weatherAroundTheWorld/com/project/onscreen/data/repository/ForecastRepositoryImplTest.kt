package com.project.weatherAroundTheWorld.com.project.onscreen.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.project.weatherAroundTheWorld.data.api.ApiService
import com.project.weatherAroundTheWorld.data.db.WeatherDb
import com.project.weatherAroundTheWorld.data.db.dao.ForecastDao
import com.project.weatherAroundTheWorld.data.db.entity.AnimeEntity
import com.project.weatherAroundTheWorld.data.mapper.DailyForecastMapper
import com.project.weatherAroundTheWorld.data.repository.ForecastRepositoryImpl
import com.project.weatherAroundTheWorld.data.response.*
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ForecastRepositoryImplTest {
    private var forecastDto= listOf(
        DailyForecastDto("",
        Temperature(Metric(23.34,"C"),),
        "Hazy Cloud",false,true
        ),)
    val apikey="ddddddddddddddddddddddd"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    lateinit var mockWebServer:MockWebServer
    lateinit var apiService: ApiService
    lateinit var forecastRepositoryImpl: ForecastRepositoryImpl

    @Mock
    lateinit var weatherDb:WeatherDb
    @Mock
    lateinit var forecastDao: ForecastDao
    @Mock
    lateinit var mapper: DailyForecastMapper
    @Mock
    lateinit var connectionUtils: ConnectionUtils
    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        Mockito.`when`(weatherDb.foreCastDao()).thenReturn(forecastDao)
        forecastRepositoryImpl = ForecastRepositoryImpl(weatherDb, apiService, dispatcher, mapper,connectionUtils)
    }
    @Test
    fun `get anime from db using flow`() = runTest {

        val forecastDomain = listOf(
            DailyForecastDomainModel(1,"Hazy Cloud" ,"7C" ,"",true,false),
        )

        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(false)
        Mockito.`when`(forecastDao.getForecast()).thenReturn(forecastDomain)
        Mockito.`when`(mapper.mapToForecastDomain(forecastDto))
            .thenReturn(forecastDomain)
        val result = forecastRepositoryImpl.getForecasts("11234",apikey).flatMapConcat { it.asFlow() }.toList()
            .get(0).isDayTime
        Assert.assertEquals(result, true)

    }

    @Test
    fun `get Employees from api using flow status success`() = runTest {

        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(true)
       `get anime quotes with http code 200`()
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `get Employees from api using flow status failed`() = runTest {
        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(true)
        `get anime quotes with http code 404`()
    }
    @Test(expected = retrofit2.HttpException::class)
    fun `get anime quotes with http code 404`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.getForecast("11234",apikey)
        Assert.assertEquals(actualResponse.size, 0)
    }

    @Test
    fun `get anime quotes with http code 200`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(forecastDto))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.getForecast("11234",apikey)
        Assert.assertEquals(actualResponse.get(0).isDayTime, true)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

}