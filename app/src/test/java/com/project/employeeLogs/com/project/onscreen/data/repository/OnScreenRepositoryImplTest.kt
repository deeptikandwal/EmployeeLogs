package com.project.employeeLogs.com.project.onscreen.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.project.employeeLogs.data.api.ApiService
import com.project.employeeLogs.data.db.EmployeeDb
import com.project.employeeLogs.data.db.dao.AnimeDao
import com.project.employeeLogs.data.db.dao.EmployeesDao
import com.project.employeeLogs.data.db.entity.AnimeEntity
import com.project.employeeLogs.data.db.entity.EmployeeEntity
import com.project.employeeLogs.data.mapper.EmployeeMapper
import com.project.employeeLogs.data.repository.EmployeeRepositoryImpl
import com.project.employeeLogs.data.response.AnimeDto
import com.project.employeeLogs.data.response.EmployeeListDto
import com.project.employeeLogs.utils.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
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
class OnScreenRepositoryImplTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    lateinit var onScreenRepositoryImpl: EmployeeRepositoryImpl

    @Mock
    lateinit var onScreenDB: EmployeeDb
    @Mock
    lateinit var connectionUtils: ConnectionUtils

    @Mock
    lateinit var dbDao: EmployeesDao

    @Mock
    lateinit var animeDao: AnimeDao

    @Mock
    lateinit var mockList: List<EmployeeEntity>

    @Mock
    lateinit var mAnimeList: List<AnimeEntity>

    @Mock
    lateinit var mapper: EmployeeMapper
    private lateinit var mockWebServer: MockWebServer
    lateinit var apiService: ApiService

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
            .create(ApiService::class.java)
        Mockito.`when`(onScreenDB.employeesDao()).thenReturn(dbDao)
        Mockito.`when`(onScreenDB.animesDao()).thenReturn(animeDao)

        onScreenRepositoryImpl = EmployeeRepositoryImpl(onScreenDB, apiService, mapper, dispatcher,connectionUtils)
    }

    @Test
    fun `get Employees from db`() = runTest {
        Mockito.`when`(dbDao.getAllEmployees()).thenReturn(mockList)
        Mockito.`when`(mockList.isEmpty()).thenReturn(false)
        Assert.assertEquals(onScreenRepositoryImpl.getEmployees().flatMapConcat { it.asFlow() }
            .toList().size, 0)
        Mockito.verify(mapper).mapToEmployeeDomainFromEntity(mockList)
    }

    @Test
    fun `get employees with http code 200`() = runTest {
        val employees = arrayListOf(
            EmployeeListDto(1, "Jones", "jonas34@gmail.com", ""),
            EmployeeListDto(2, "Samantha", "sam@gmail.com", "")
        )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(employees))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.fetchEmployees()
        Assert.assertEquals(actualResponse.get(0).email, "jonas34@gmail.com")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `get employees with http code 400`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(Gson().toJson(null))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = apiService.fetchEmployees()
        Assert.assertEquals(actualResponse.size, 0)

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

    @Test
    fun `get Animes from db`() = runTest(dispatcher) {

        Mockito.`when`(animeDao.getAllAnimes()).thenReturn(mAnimeList)
        Mockito.`when`(animeDao.getAnimeByName(null)).thenReturn(AnimeEntity())
        Mockito.`when`(mAnimeList.isEmpty()).thenReturn(false)

        Assert.assertEquals(onScreenRepositoryImpl.getAnimeList(null).flatMapConcat { it.asFlow() }
            .toList().size, 0)
        Mockito.verify(mapper).mapToAnimeDomainFromEntity(mAnimeList)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}