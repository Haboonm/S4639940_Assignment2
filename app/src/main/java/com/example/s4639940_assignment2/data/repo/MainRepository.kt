package com.example.s4639940_assignment2.data.repo

import android.util.Log
import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.data.model.LoginRequest
import com.example.s4639940_assignment2.data.remote.ApiService
import retrofit2.HttpException

class MainRepository(private val api: ApiService) {

    suspend fun login(firstname: String, studentId: String): String {
        val body = LoginRequest(username = firstname.trim().lowercase(), password = studentId.trim())

        return try {
            val res = api.login(body)
            Log.d("LOGIN_DEBUG", "Success! keypass = ${res.keypass}")
            res.keypass
        } catch (e: HttpException) {
            val err = e.response()?.errorBody()?.string()
            Log.e("LOGIN_DEBUG", "HTTP ${e.code()} error: $err")
            throw e
        } catch (e: Exception) {
            Log.e("LOGIN_DEBUG", "Network or JSON error: ${e.message}", e)
            throw e
        }
    }

    suspend fun loadDashboard(keypass: String): List<DashboardItem> {
        return api.getDashboard(keypass)
    }
}
