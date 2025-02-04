package com.example.twintpoc.di

import com.example.twintpoc.repository.DataRepository
import com.example.twintpoc.repository.DataRepositoryInt
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): DataRepositoryInt
}
