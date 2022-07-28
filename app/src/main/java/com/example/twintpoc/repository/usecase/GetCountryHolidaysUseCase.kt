package com.example.twintpoc.repository.usecase

import com.example.twintpoc.network.Resource
import com.example.twintpoc.network.model.Holiday
import com.example.twintpoc.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCountryHolidaysUseCase @Inject constructor(private val dataRepository: DataRepository) {
    suspend fun requestHolidays(country: String, year: String): Flow<Resource<List<Holiday>>> =
        dataRepository.requestHolidays(country, year)
}