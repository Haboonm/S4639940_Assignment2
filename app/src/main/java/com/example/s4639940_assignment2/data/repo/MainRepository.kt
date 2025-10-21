package com.example.s4639940_assignment2.data.repo

import android.util.Log
import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.data.model.LoginRequest
import com.example.s4639940_assignment2.data.remote.ApiService
import retrofit2.HttpException

class MainRepository(private val api: ApiService) {

    suspend fun login(firstname: String, studentId: String): String {
        val body = LoginRequest(
            username = firstname.trim().lowercase(),
            password = studentId.trim()
        )

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
        Log.d("DASH_DEBUG", "Requesting dashboard for keypass='$keypass'")
        val dto = api.getDashboard(keypass) // returns ApiDashboardResponse

        // Map API shape (books) -> UI model (DashboardItem)
        val items = dto.entities.map { book ->
            val title = book.title.orEmpty()
            val author = book.author.orEmpty()
            val genre = book.genre.orEmpty()
            val year = (book.publicationYear ?: 0).toString()
            val desc = book.description.orEmpty()

            DashboardItem(
                id = "${title}_${author}".ifBlank { title.ifBlank { author } }.lowercase(),
                title = title,                 // e.g., "To Kill a Mockingbird"
                subtitle = author,             // e.g., "Harper Lee"
                date = year,                   // e.g., "1960"
                location = genre,              // e.g., "Fiction"
                description = desc             // long description
            )
        }

        Log.d("DASH_DEBUG", "Mapped ${items.size} items")
        return items
    }
}
