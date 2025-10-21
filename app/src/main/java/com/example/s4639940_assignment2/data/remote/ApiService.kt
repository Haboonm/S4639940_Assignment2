package com.example.s4639940_assignment2.data.remote

import com.example.s4639940_assignment2.data.model.LoginRequest
import com.example.s4639940_assignment2.data.model.LoginResponse
import com.example.s4639940_assignment2.data.model.api.ApiDashboardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("footscray/auth")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    // NOTE: API returns { "entities": [ ... ], "entityTotal": N }
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): ApiDashboardResponse
}
