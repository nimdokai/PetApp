package com.nimdokai.pet.core_network.api

import com.nimdokai.pet.core_network.model.CurrentConditionsJsonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AccuWeatherApi {

    @GET("currentconditions/v1/{locationId}")
    suspend fun getCurrentConditionsForLocation(
        @Path("locationId") locationId: String
    ): Response<List<CurrentConditionsJsonResponse>>

}