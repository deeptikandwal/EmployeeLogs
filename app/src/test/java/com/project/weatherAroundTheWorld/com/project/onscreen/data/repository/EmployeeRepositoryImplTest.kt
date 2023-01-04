package com.project.weatherAroundTheWorld.com.project.onscreen.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.project.weatherAroundTheWorld.data.api.ApiService
import com.project.weatherAroundTheWorld.data.db.WeatherDb
import com.project.weatherAroundTheWorld.data.db.dao.CitiesDao
import com.project.weatherAroundTheWorld.data.db.entity.EmployeeEntity
import com.project.weatherAroundTheWorld.utils.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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
class EmployeeRepositoryImplTest {
    private var employees = arrayListOf(
        EmployeeListDto(1, "Jones", "jonas34@gmail.com", ""),
        EmployeeListDto(2, "Samantha", "sam@gmail.com", "")
    )

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    lateinit var employeeRepositoryImpl: EmployeeRepositoryImpl

    @Mock
    lateinit var employeeDb: WeatherDb

    @Mock
    lateinit var connectionUtils: ConnectionUtils

    @Mock
    lateinit var dbDao: CitiesDao


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
        Mockito.`when`(employeeDb.employeesDao()).thenReturn(dbDao)
        employeeRepositoryImpl =
            EmployeeRepositoryImpl(employeeDb, apiService, mapper, dispatcher, connectionUtils)
    }

    @Test
    fun `get Employees from db using flow`() = runTest {

        val employeeDomain = listOf(
            EmployeeDomainModel(1, "James", "james@gmail.com", ""),
            EmployeeDomainModel(2, "Purna", "purna13@orkut.in", "")
        )
        val employeeEntity = listOf<EmployeeEntity>()

        Mockito.`when`(connectionUtils.isNetworkAvailable()).thenReturn(false)
        Mockito.`when`(dbDao.getAllEmployees()).thenReturn(employeeEntity)
        Mockito.`when`(mapper.mapToEmployeeDomainFromEntity(employeeEntity))
            .thenReturn(employeeDomain)
        val result = employeeRepositoryImpl.getEmployees().flatMapConcat { it.asFlow() }.toList()
            .get(1).email
        Assert.assertEquals(result, "purna13@orkut.in")

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


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}