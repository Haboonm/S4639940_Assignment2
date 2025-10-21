package com.example.s4639940_assignment2.data.repo

import android.util.Log
import com.example.s4639940_assignment2.data.model.DashboardItem
import com.example.s4639940_assignment2.data.model.LoginRequest
import com.example.s4639940_assignment2.data.remote.ApiService
import retrofit2.HttpException

// Repository = single source of truth for network data.
// Hides Retrofit details and maps API models into UI models.
class MainRepository(private val api: ApiService) {

    // Call auth endpoint and return the keypass string.
    suspend fun login(firstname: String, studentId: String): String {
        // Clean input and build request body
        val body = LoginRequest(
            username = firstname.trim().lowercase(),
            password = studentId.trim()
        )

        return try {
            val res = api.login(body)
            Log.d("LOGIN_DEBUG", "Success! keypass = ${res.keypass}")
            res.keypass
        } catch (e: HttpException) {
            // Show server error details in Logcat (useful for debugging)
            val err = e.response()?.errorBody()?.string()
            Log.e("LOGIN_DEBUG", "HTTP ${e.code()} error: $err")
            throw e
        } catch (e: Exception) {
            // Covers no internet / parse errors etc.
            Log.e("LOGIN_DEBUG", "Network or JSON error: ${e.message}", e)
            throw e
        }
    }

    // Fetch list of books and map them to DashboardItem for the UI.
    suspend fun loadDashboard(keypass: String): List<DashboardItem> {
        Log.d("DASH_DEBUG", "Requesting dashboard for keypass='$keypass'")
        // API returns wrapper object -> we take entities[]
        val dto = api.getDashboard(keypass) // returns ApiDashboardResponse

        // Map API "book" shape → our UI model "DashboardItem"

        val items = dto.entities.map { book ->
            val title = book.title.orEmpty()     // e.g., "To Kill a Mockingbird"
            val author = book.author.orEmpty()    // e.g., "Harper Lee"
            val genre = book.genre.orEmpty()      // e.g., "Fiction"
            val year = (book.publicationYear ?: 0).toString()   // e.g., "1960"
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
