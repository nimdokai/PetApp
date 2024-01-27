package com.nimdokai.pet.core.data.di

import com.nimdokai.pet.core.data.repositories.WeatherRepository
import com.nimdokai.pet.core.data.repositories.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinder {

    @Singleton
    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

}