package com.example.twintpoc.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weekday(
    @Json(name = "date")
    val date: Date,
    @Json(name = "observed")
    val observed: Observed
)