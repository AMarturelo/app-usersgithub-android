package com.amarturelo.usersgithub.data.datasource.remote

import com.amarturelo.usersgithub.commons.utils.Either
import com.amarturelo.usersgithub.data.api.UserAPI
import com.amarturelo.usersgithub.data.model.UserModel
import com.amarturelo.usersgithub.di.module.NetworkModule
import com.amarturelo.usersgithub.exception.Failure
import com.amarturelo.usersgithub.utils.FakeValuesModel
import com.google.gson.Gson
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDataSourceRemoteTest {
    @get:Rule
    val mockWebServer = MockWebServer()

    lateinit var gson: Gson

    private lateinit var api: UserAPI

    @InjectMockKs
    private lateinit var dataSource: UserDataSourceRemote

    @Before
    fun setUp() {
        val networkModule = NetworkModule()
        gson = networkModule.providesGson()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(networkModule.providesOkHttpClientBuilder())
            .build()
            .create(UserAPI::class.java)

        dataSource = UserDataSourceRemote(api)
    }

    @Test
    fun `given success Users when users then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesModel.users()
        mockWebServer.enqueue(MockResponse().apply {
            setBody(gson.toJson(fakeResult))
            setResponseCode(200)
        })

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right(fakeResult), result)
        Assert.assertFalse(result.isLeft)
        Assert.assertTrue(result.isRight)
    }

    @Test
    fun `given empty Response when users then verify result`() = runTest {
        //given
        mockWebServer.enqueue(MockResponse().apply {
            setBody("[]")
            setResponseCode(200)
        })

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right<List<UserModel>>(listOf()), result)
        Assert.assertFalse(result.isLeft)
        Assert.assertTrue(result.isRight)
    }

    @Test
    fun `given fail result when users then verify result`() = runTest {
        //given
        mockWebServer.enqueue(MockResponse().apply {
            setBody("")
            setResponseCode(500)
        })

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(result, Either.Left(Failure.UnknownError))
        Assert.assertTrue(result.isLeft)
        Assert.assertFalse(result.isRight)
    }
}