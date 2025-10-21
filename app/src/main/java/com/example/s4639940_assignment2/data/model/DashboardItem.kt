package com.example.s4639940_assignment2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// UI-friendly model that our RecyclerView + Details screen use.
// We convert the API book shape into this in MainRepository.
// Parcelable so we can pass it through Navigation arguments (Bundle).
@Parcelize
data class DashboardItem(
    val id: String,          // stable key for list diffing (eg: "title_author")
    val title: String,        // main line (book title)
    val subtitle: String,    // secondary line (author)
    val date: String,        // shown in meta (year)
    val location: String,    // shown in meta (genre)
    val description: String  // full description for details screen
) : Parcelable