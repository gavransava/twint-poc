package com.example.twintpoc.repository

import com.example.twintpoc.network.Resource
import com.example.twintpoc.network.model.Holiday
import kotlinx.coroutines.flow.Flow

interface DataRepositoryInt {
    suspend fun requestHolidays(country: String, year: String): Flow<Resource<ArrayList<Holiday>>>
}