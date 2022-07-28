package com.example.twintpoc.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Requests(
    @Json(name = "available")
    val available: Int,
    @Json(name = "resets")
    val resets: String,
    @Json(name = "used")
    val used: Int
)