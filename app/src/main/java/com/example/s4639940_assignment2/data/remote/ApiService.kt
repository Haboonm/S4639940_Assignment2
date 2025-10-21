package com.example.s4639940_assignment2.data.remote

import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.data.model.LoginRequest
import com.example.s4639940_assignment2.data.model.LoginResponse
import retrofit2.http.*

interface ApiService {


    @POST("footscray/auth")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    // The spec says dashboard is /dashboard/{keypass}
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): List<DashboardItem>
}
