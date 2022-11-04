package kacper.litwinow.yournewsweek.api

import android.os.Build
import androidx.test.runner.AndroidJUnit4
import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import kacper.litwinow.yournewsweek.api.data.remote.Posts
import kacper.litwinow.yournewsweek.api.service.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.internal.bytecode.RobolectricInternals
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class ApiServiceTest {

    private var mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


    private lateinit var apiService: ApiService

    lateinit var expectedData: Posts

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .build()
        .create(ApiService::class.java)

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(ApiService::class.java)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `the service should fetch movies correctly given 200 response`() = runBlocking {
        mockWebServer.enqueueResponse("posts.json", 200)


    }

    @Test
    fun testSuccessfulResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)

            }
        }
    }
    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        javaClass.classLoader?.getResourceAsStream(fileName)
            ?.source()
            ?.buffer()
            .also { source ->
                source?.run {
                    enqueue(
                        MockResponse()
                            .setResponseCode(code)
                            .setBody(source.readString(StandardCharsets.UTF_8))
                    )
                }
            }
    }
}