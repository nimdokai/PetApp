package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentConditionsUseCaseImpl @Inject constructor(
    private val repo: WeatherRepository
) : GetCurrentConditionsUseCase {

    //ToDo is is correct to create a flow every time?
    override suspend fun invoke(): Flow<DomainResult<out CurrentConditions>> = flow {

        // ToDo pass the location
        val locationId = "326967"
        // ToDo get unit from configs
        val unit = "C"

        when (val categoriesResponse = repo.getCurrentConditions(locationId, unit)){
            is DataResponse.Success -> emit(DomainResult.Success(categoriesResponse.data))
            DataResponse.NoInternet -> emit(DomainResult.NoInternet)
            DataResponse.ServerError -> emit(DomainResult.ServerError)
        }
    }

}