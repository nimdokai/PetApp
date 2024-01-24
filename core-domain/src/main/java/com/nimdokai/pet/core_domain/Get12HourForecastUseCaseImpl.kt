package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.model.HourlyForecast
import com.nimdokai.pet.core.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Get12HourForecastUseCaseImpl @Inject constructor(
    private val repo: WeatherRepository
) : GetHourlyForecastUseCase {

    //ToDo is is correct to create a flow every time?
    override fun invoke(): Flow<DomainResult<out List<HourlyForecast>>> = flow {

        // ToDo pass the location
        val locationId = "326967"
        // ToDo get unit from configs
        val useMetrics = true

        when (val categoriesResponse = repo.get12HourForecast(locationId, useMetrics)){
            is DataResponse.Success -> emit(DomainResult.Success(categoriesResponse.data))
            DataResponse.NoInternet -> emit(DomainResult.NoInternet)
            DataResponse.ServerError -> emit(DomainResult.ServerError)
        }
    }

}