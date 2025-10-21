package com.example.s4639940_assignment2.data.model
import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "keypass") val keypass: String
)