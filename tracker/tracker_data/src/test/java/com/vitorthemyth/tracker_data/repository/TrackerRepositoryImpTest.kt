package com.vitorthemyth.tracker_data.repository

import com.google.common.truth.Truth.assertThat
import com.vitorthemyth.tracker_data.remote.OpenFoodApi
import com.vitorthemyth.tracker_data.remote.malformedFoodResponse
import com.vitorthemyth.tracker_data.remote.validFoodResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class TrackerRepositoryImpTest {

    private lateinit var repository: TrackerRepositoryImp
    private lateinit var mockWebService: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: OpenFoodApi
    @Before
    fun setUp() {
        mockWebService = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1,TimeUnit.SECONDS)
            .readTimeout(1,TimeUnit.SECONDS)
            .connectTimeout(1,TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebService.url("/"))
            .build()
            .create(OpenFoodApi::class.java)

        repository = TrackerRepositoryImp(
            dao = mockk(relaxed = true),
            api = api
        )
    }

   @After
   fun tearDown(){
       mockWebService.shutdown()
   }

    @Test
    fun `Search food,valid response,returns results`() = runBlocking{
        mockWebService.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validFoodResponse)
        )
        val result = repository.searchFood("banana", page = 1, pageSize = 40)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `Search food,invalid response,returns failure`() = runBlocking{
        mockWebService.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(malformedFoodResponse)
        )
        val result = repository.searchFood("banana", page = 1, pageSize = 40)

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `Search food,malformated response,returns failure`() = runBlocking{
        mockWebService.enqueue(
            MockResponse()
                .setBody(malformedFoodResponse)
        )
        val result = repository.searchFood("banana", page = 1, pageSize = 40)

        assertThat(result.isFailure).isTrue()
    }
}