package com.project.employeeLogs.com.project.onscreen.views.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.employeeLogs.domain.usecase.GetAnimesUseCase
import com.project.employeeLogs.domain.model.AnimeDomainModel
import com.project.employeeLogs.views.viewmodel.AnimeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
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
class AnimeViewModelTest {
    @Mock
    private lateinit var getAnimesUseCase: GetAnimesUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    private lateinit var animeViewModel: AnimeViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        animeViewModel = AnimeViewModel(getAnimesUseCase)
        animeViewModel.getAnimesUseCase = getAnimesUseCase
    }

    @Test
    fun handleOperationSuccessTest(): Unit = runTest(dispatcher) {
        Mockito.`when`(getAnimesUseCase.invoke("Naruto")).thenReturn(flowOf( listOf(
            AnimeDomainModel(1,"Naruto","pain")
        )))

        val animeList=getAnimesUseCase.invoke("Naruto").flatMapConcat { it.asFlow()}.toList()
        Assert.assertNotEquals(animeList.size, 0)
        Assert.assertEquals(animeList.get(0).anime, "Naruto")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun handleOperationFailTest(): Unit = runTest(dispatcher) {
        Mockito.`when`((getAnimesUseCase).invoke("null")).thenThrow(
            HttpException(
                Response.error<Any>(
                    409,
                    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
                )
            )
        )
        Assert.assertEquals(getAnimesUseCase.invoke("null").flatMapConcat { it.asFlow()}.toList().size, 0)

    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}