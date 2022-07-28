package com.example.twintpoc.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Date(
    @Json(name = "name")
    val name: String,
    @Json(name = "numeric")
    val numeric: String
)