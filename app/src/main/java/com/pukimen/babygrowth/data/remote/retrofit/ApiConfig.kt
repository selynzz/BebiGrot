package com.pukimen.babygrowth.data.remote.retrofit

import com.pukimen.babygrowth.BuildConfig
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        val baseUrl = BuildConfig.BASE_URL
        val baseUrlNinja = BuildConfig.BASE_URL_NINJA
        val baseUrlRecomendation = BuildConfig.BASE_URL_RECOMENDATION
        val apiKey = BuildConfig.API_KEY

        private fun createOkHttpClient(interceptors: List<Interceptor>): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectionSpecs(listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
                .connectTimeout(10, TimeUnit.SECONDS)  // Connection timeout
                .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
                .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout

            interceptors.forEach { builder.addInterceptor(it) }

            // Retry logic
            builder.retryOnConnectionFailure(true)

            return builder.build()
        }

        private fun createRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        fun getApiService(): ApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val authInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder().build()
                chain.proceed(request)
            }

            val client = createOkHttpClient(listOf(authInterceptor, logging))
            return createRetrofit(baseUrl, client).create(ApiService::class.java)
        }

        fun getRecomendationApiService(): ApiService {
            val authInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder().build()
                chain.proceed(request)
            }

            val client = createOkHttpClient(listOf(authInterceptor))
            return createRetrofit(baseUrlRecomendation, client).create(ApiService::class.java)
        }

        fun getNinjaApiService(): ApiService {
            val authInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", apiKey)
                    .build()
                chain.proceed(request)
            }

            val client = createOkHttpClient(listOf(authInterceptor))
            return createRetrofit(baseUrlNinja, client).create(ApiService::class.java)
        }
    }
}