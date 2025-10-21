package com.example.s4639940_assignment2.di

import com.example.s4639940_assignment2.data.remote.ApiService
import com.example.s4639940_assignment2.data.repo.MainRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    // --- JSON (Moshi)
    single {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    // --- OkHttp
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    // --- Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://nit3213api.onrender.com/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    // --- ApiService
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }

    // --- Repository
    single { MainRepository(get()) }

    // --- ViewModels (constructor injection)
    viewModel { com.example.s4639940_assignment2.ui.login.LoginViewModel(get()) }
    viewModel { com.example.s4639940_assignment2.ui.dashboard.DashboardViewModel(get()) }
}
