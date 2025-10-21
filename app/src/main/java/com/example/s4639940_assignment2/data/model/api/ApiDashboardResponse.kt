package com.example.s4639940_assignment2.data.model.api

import com.squareup.moshi.Json

data class ApiDashboardResponse(
    @Json(name = "entities") val entities: List<ApiBook> = emptyList(),
    @Json(name = "entityTotal") val entityTotal: Int? = null
)

data class ApiBook(
    @Json(name = "title") val title: String? = null,
    @Json(name = "author") val author: String? = null,
    @Json(name = "genre") val genre: String? = null,
    @Json(name = "publicationYear") val publicationYear: Int? = null,
    @Json(name = "description") val description: String? = null
)
