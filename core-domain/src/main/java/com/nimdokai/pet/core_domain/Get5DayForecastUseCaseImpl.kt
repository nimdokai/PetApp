package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.model.DailyForecast
import com.nimdokai.pet.core.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Get5DayForecastUseCaseImpl @Inject constructor(
    private val repo: WeatherRepository
) : GetDailyForecastUseCase {

    //ToDo is is correct to create a flow every time?
    override fun invoke(): Flow<DomainResult<out List<DailyForecast>>> = flow {

        // ToDo pass the location
        val locationId = "326967"
        // ToDo get unit from configs
        val useMetrics = true

        when (val categoriesResponse = repo.get5DayForecast(locationId, useMetrics)) {
            is DataResponse.Success -> emit(DomainResult.Success(categoriesResponse.data))
            DataResponse.NoInternet -> emit(DomainResult.NoInternet)
            DataResponse.ServerError -> emit(DomainResult.ServerError)
        }
    }

}