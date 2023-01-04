package com.project.weatherAroundTheWorld.com.project.onscreen.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.project.weatherAroundTheWorld.data.api.ApiService
import com.project.weatherAroundTheWorld.data.db.WeatherDb
import com.project.weatherAroundTheWorld.data.db.dao.ForecastDao
import com.project.weatherAroundTheWorld.data.db.entity.AnimeEntity
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

class AnimeRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    lateinit var mockWebServer:MockWebServer
    lateinit var apiService: ApiService
    lateinit var animeRepositoryImpl: AnimeRepositoryImpl

    @Mock
    lateinit var employeeDb:WeatherDb
    @Mock
    lateinit var animeDao: ForecastDao
    @Mock
    lateinit var mapper: AnimeMapper
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
        Mockito.`when`(employeeDb.animesDao()).thenReturn(animeDao)
        animeRepositoryImpl = AnimeRepositoryImpl(employeeDb, apiService, mapper, dispatcher, connectionUtils)
    }
    @Test
    fun `get anime from db using flow`() = runTest {

        val animeDomain = listOf(
            AnimeDomainModel(1, "Naruto", "It's my nindo",),
            AnimeDomainModel(2, "Death Note", "")
        )
        val animeEntity = listOf(AnimeEntity(1, "Naruto", "It's my nindo"))

        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(false)
        Mockito.`when`(animeDao.getAllAnimes()).thenReturn(animeEntity)
        Mockito.`when`(animeDao.getAnimeByName("Naruto")).thenReturn(AnimeEntity(1, "Naruto", "It's my nindo"))
        Mockito.`when`(mapper.mapToAnimeDomainFromEntity(animeEntity))
            .thenReturn(animeDomain)
        val result = animeRepositoryImpl.getAnimeList("Naruto").flatMapConcat { it.asFlow() }.toList()
            .get(0).quote
        Assert.assertEquals(result, "It's my nindo")

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
        val actualResponse = apiService.fetchAnimeUsingTitle("bbbbbbb")
        Assert.assertEquals(actualResponse.size, 0)
    }

    @Test
    fun `get anime quotes with http code 200`() = runTest {
        val employees = arrayListOf(
            AnimeDto(1, "Naruto", "pain", "This is my nindo"),
        )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(employees))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.fetchAnimeUsingTitle("Naruto")
        Assert.assertEquals(actualResponse.get(0).character, "pain")
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

}