package com.example.twintpoc.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HolidayResponse(
    @Json(name = "holidays")
    val holidays: List<Holiday>,
    @Json(name = "requests")
    val requests: Requests,
    @Json(name = "status")
    val status: Int,
    @Json(name = "warning")
    val warning: String
)