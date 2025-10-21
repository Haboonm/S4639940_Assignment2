package com.example.s4639940_assignment2.data.model
import com.squareup.moshi.Json

// Body we send to POST /footscray/auth
data class LoginRequest(
    @Json(name = "username") val username: String,  // first name (lowercased)
    @Json(name = "password") val password: String   // student ID
)