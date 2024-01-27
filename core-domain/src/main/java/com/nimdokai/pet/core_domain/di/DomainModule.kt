package com.nimdokai.pet.core_domain.di

import com.nimdokai.pet.core_domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindGetCurrentConditionsUseCase(impl: GetCurrentConditionsUseCaseImpl): GetCurrentConditionsUseCase

    @Binds
    fun bindGet12HourForecastUseCase(impl: Get12HourForecastUseCaseImpl): GetHourlyForecastUseCase

    @Binds
    fun bindGet5DayForecastUseCaseImpl(impl: Get5DayForecastUseCaseImpl): GetDailyForecastUseCase

}