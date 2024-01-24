package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.model.HourlyForecast
import kotlinx.coroutines.flow.Flow

interface GetHourlyForecastUseCase {

    operator fun invoke(): Flow<DomainResult<out List<HourlyForecast>>>

}