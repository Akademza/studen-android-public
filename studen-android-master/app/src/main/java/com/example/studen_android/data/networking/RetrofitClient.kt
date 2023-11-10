package com.example.studen_android.data.networking

import com.example.studen_android.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = BuildConfig.BASE_URL
    private val contentType = "application/json".toMediaType()
    private val jsonFormat = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        isLenient = true
        allowStructuredMapKeys = true
    }

    private val jsonConverterFactory by lazy {
        jsonFormat.asConverterFactory(contentType)
    }

    val endpointsInterface = buildEndpointsInterfaceAPI(BASE_URL)

    private fun buildEndpointsInterfaceAPI(baseURL: String): EndpointsInterface {
        val okHttpClient = provideOkHttpClientBuilder().apply {
            addLoggingInterceptorIfNeeded()
        }
            .build()
        val client = provideRetrofitClient(okHttpClient, baseURL)
        return client.create(EndpointsInterface::class.java)
    }

    private fun provideOkHttpClientBuilder() = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }

    private fun OkHttpClient.Builder.addLoggingInterceptorIfNeeded() {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }
    }

    private fun provideRetrofitClient(okHttpClient: OkHttpClient, baseURL: String) = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(jsonConverterFactory)
        .client(okHttpClient)
        .build()

}