package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.model.DailyForecast
import kotlinx.coroutines.flow.Flow

interface Get5DayForecastUseCase {

    suspend operator fun invoke(): Flow<DomainResult<out List<DailyForecast>>>

}