package com.example.s4639940_assignment2.data.model
import com.squareup.moshi.Json

// Response from POST /footscray/auth
// Contains the keypass token used to fetch dashboard data.
data class LoginResponse(
    @Json(name = "keypass") val keypass: String
)