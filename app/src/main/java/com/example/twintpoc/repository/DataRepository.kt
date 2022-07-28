package com.example.twintpoc.repository

import com.example.twintpoc.network.Resource
import com.example.twintpoc.network.model.Holiday
import com.example.twintpoc.network.model.HolidayResponse
import com.example.twintpoc.data.error.Error.Companion.NETWORK_ERROR
import com.example.twintpoc.data.error.Error.Companion.NO_INTERNET_CONNECTION
import com.example.twintpoc.network.HolidayService
import com.example.twintpoc.util.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(
    private val networkConnectivity: NetworkConnectivity,
    private val holidayService: HolidayService,
    private val ioDispatcher: CoroutineContext
) : DataRepositoryInt {
    override suspend fun requestHolidays(country: String, year: String): Flow<Resource<List<Holiday>>> {
        return flow {
            emit(
                when (val response = processCall(holidayService, country, year)) {
                    is HolidayResponse -> {
                        Resource.Success(data = response.holidays)
                    }
                    else -> {
                        Resource.DataError(errorCode = response as Int)
                    }
                }
            )
        }.flowOn(ioDispatcher)
    }

    private suspend fun processCall(holidayService: HolidayService, country: String, year: String): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = holidayService.fetchPublicHolidays(country = country, year = year)
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
