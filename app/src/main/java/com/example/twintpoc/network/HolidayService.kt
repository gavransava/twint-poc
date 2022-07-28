package com.example.twintpoc.network

import com.example.twintpoc.BuildConfig.API_KEY
import com.example.twintpoc.network.model.HolidayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayService {
    @GET("/v1/holidays")
    suspend fun fetchPublicHolidays(
        @Query("key") key: String = API_KEY,
        @Query("country") country: String,
        @Query("year") year: String,
        @Query("public") public: Boolean = true
    ): Response<HolidayResponse>
}