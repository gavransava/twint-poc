package com.example.twintpoc.repository

import com.example.twintpoc.data.Resource
import com.example.twintpoc.network.model.Holiday
import com.example.twintpoc.network.model.HolidayResponse
import com.example.twintpoc.data.error.Error.Companion.NETWORK_ERROR
import com.example.twintpoc.data.error.Error.Companion.NO_INTERNET_CONNECTION
import com.example.twintpoc.network.HolidayService
import com.example.twintpoc.util.NetworkConnectivity
import java.io.IOException
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val holidayService: HolidayService
) {
    suspend fun requestHolidays(): Resource<ArrayList<Holiday>> {
        return when (val response = processCall(holidayService)) {
            is HolidayResponse -> {
                Resource.Success(data = response.holidays)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(holidayService: HolidayService): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = holidayService.fetchPublicHolidays(country = "RS", year = "2022")
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            return NETWORK_ERROR
        }
    }
}
