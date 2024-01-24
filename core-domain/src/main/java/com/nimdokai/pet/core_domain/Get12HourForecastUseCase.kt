package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.model.HourlyForecast
import kotlinx.coroutines.flow.Flow

interface Get12HourForecastUseCase {

    suspend operator fun invoke(): Flow<DomainResult<out List<HourlyForecast>>>

}