package com.example.s4639940_assignment2.data.remote

import com.example.s4639940_assignment2.data.repo.MainRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory   // <-- add this
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Simple DI holder to build Retrofit/Moshi/OkHttp once and reuse.
// Access as ServiceLocator.api and ServiceLocator.repo.
object ServiceLocator {

    // Base URL for the provided assignment API
    private const val BASE_URL = "https://nit3213api.onrender.com/"

    // JSON parser: add Kotlin adapter so Moshi handles Kotlin nullability/data classes nicely
    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())                    // <-- add this
            .build()
    }

    // OkHttp with body-level logging (handy while developing)
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    // Retrofit client wired with Moshi and our base URL
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Expose API + Repository singletons
    val api: ApiService by lazy { retrofit.create(ApiService::class.java) }
    val repo: MainRepository by lazy { MainRepository(api) }
}
