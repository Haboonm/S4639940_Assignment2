package com.example.s4639940_assignment2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: String,
    val location: String,
    val description: String
) : Parcelable