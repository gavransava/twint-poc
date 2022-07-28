package com.example.twintpoc.di

import com.example.twintpoc.repository.DataRepository
import com.example.twintpoc.repository.usecase.GetCountryHolidaysUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCase(dataRepository: DataRepository): GetCountryHolidaysUseCase =
        GetCountryHolidaysUseCase(dataRepository)

}