package com.m4ykey.data.spotify

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.m4ykey.data.remote.api.AuthApi
import com.m4ykey.data.remote.interceptor.SpotifyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class SpotifyInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var context : Context

    @Before
    fun setupMockWebServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSpotifyInterceptorWithNoInternet() {
        val responseBody = """{"access_token": "mocked_token", "token_type": "bearer", "expires_in": 3600}"""
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(responseBody)
        mockWebServer.enqueue(response)

        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        try {

            val interceptor = SpotifyInterceptor(
                createMockAuthApi(),
                createMockDataStore(context)
            )

            val request = Request.Builder()
                .url(mockWebServer.url("/test-endpoint"))
                .build()

            interceptor.intercept(object : Interceptor.Chain {
                override fun call(): Call {
                    throw NotImplementedError("Not implemented")
                }

                override fun connectTimeoutMillis(): Int {
                    return 0
                }

                override fun connection(): Connection? {
                    return null
                }

                override fun proceed(request: Request): Response {
                    throw AssertionError("Request should not be proceeded")
                }

                override fun readTimeoutMillis(): Int {
                    return 0
                }

                override fun request(): Request {
                    return request
                }

                override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                    return this
                }

                override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                    return this
                }

                override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                    return this
                }

                override fun writeTimeoutMillis(): Int {
                    return 0
                }
            })

            fail("Expected UnknownHostException but no exception was thrown")
        } catch (e : UnknownHostException) {
            assertThat(e.message).isEqualTo("Expected error message")
        }
    }

    private fun createMockAuthApi(): AuthApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AuthApi::class.java)
    }

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "mock_key")

    private fun createMockDataStore(@ApplicationContext context: Context) : DataStore<Preferences> {
        return context.dataStore
    }

}