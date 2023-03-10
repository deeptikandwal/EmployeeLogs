package com.project.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.project.db.dao.CitiesDao
import com.project.db.database.WeatherDb
import com.project.db.entity.CitiesEntity
import com.project.mapper.CitiesMapper
import com.project.response.CitiesDto
import com.project.response.Country
import com.project.response.GeoPosition
import com.project.response.Region
import com.project.weatherAroundTheWorld.utils.ApiConstants
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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


@OptIn(ExperimentalCoroutinesApi::class)
class CitiesRepositoryImplTest {
    private var citiesDto= listOf(
        CitiesDto("Berlin","Germany","11234", Region("Europe"), Country("Germany"),
        GeoPosition("23.45","45.56")
    ),)
    val apikey="ddddddddddddddddddddddd"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    lateinit var citiesRepositoryImpl: CitiesRepositoryImpl

    @Mock
    lateinit var weatherDb: WeatherDb

    @Mock
    lateinit var connectionUtils: ConnectionUtils

    @Mock
    lateinit var dbDao: CitiesDao


    @Mock
    lateinit var mapper: CitiesMapper

    private lateinit var mockWebServer: MockWebServer
    lateinit var apiService: com.project.api.ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.project.api.ApiService::class.java)
        Mockito.`when`(weatherDb.citiesDao()).thenReturn(dbDao)
        citiesRepositoryImpl = CitiesRepositoryImpl(weatherDb, apiService,  dispatcher,mapper, connectionUtils)
    }

    @Test
    fun `get Employees from db using flow`() = runTest {
        val citiesDomainList = listOf(
           CitiesEntity(
                1,
                "11234",
                "Europe(23.45,45.56)",
                "Berlin(Germany)"
            ),
        )

        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(false)
        Mockito.`when`(dbDao.getAlLCities()).thenReturn(citiesDomainList)
        Mockito.`when`(mapper.mapCitiesToEntity(citiesDto))
            .thenReturn(citiesDomainList)
        val result = citiesRepositoryImpl.fetchCitiesList(ApiConstants.WEATHER_API_KEY).flatMapConcat { it.asFlow() }.toList()
            .get(0).city
        Assert.assertEquals(result, "Berlin(Germany)")

    }

    @Test
    fun `get Employees from api using flow status success`() = runTest {

        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(true)
        `get employees with http code 200`()
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `get Employees from api using flow status failed`() = runTest {
        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(true)
        `get employees with http code 400`()
    }


    @Test
    fun `get employees with http code 200`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(citiesDto))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.fetchCitiesList("50",apikey)
        Assert.assertEquals(actualResponse.get(0).localizedName, "Berlin")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `get employees with http code 400`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(Gson().toJson(null))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.fetchCitiesList("50",apikey)
        Assert.assertEquals(actualResponse.size, 0)

    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}