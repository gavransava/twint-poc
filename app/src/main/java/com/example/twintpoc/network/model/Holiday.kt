package com.example.twintpoc.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Holiday(
    @Json(name = "country")
    val country: String,
    @Json(name = "date")
    val date: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "observed")
    val observed: String,
    @Json(name = "public")
    val public: Boolean,
    @Json(name = "uuid")
    val uuid: String,
    @Json(name = "weekday")
    val weekday: Weekday
)