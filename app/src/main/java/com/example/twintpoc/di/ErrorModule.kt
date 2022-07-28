package com.example.twintpoc.di

import com.example.twintpoc.data.error.ErrorManager
import com.example.twintpoc.data.error.ErrorUseCase
import com.example.twintpoc.data.error.mapper.ErrorMapper
import com.example.twintpoc.data.error.mapper.ErrorMapperSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}
