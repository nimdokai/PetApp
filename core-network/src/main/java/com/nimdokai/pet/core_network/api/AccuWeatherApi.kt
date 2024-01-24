package com.nimdokai.pet.core_network.api

import com.nimdokai.pet.core_network.model.CurrentConditionsJson
import com.nimdokai.pet.core_network.model.DailyForecastJsonResponse
import com.nimdokai.pet.core_network.model.HourlyForecastJsonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeatherApi {

    @GET("currentconditions/v1/{locationId}")
    suspend fun getCurrentConditionsForLocation(
        @Path("locationId") locationId: String,
        @Query("details") details: Boolean = true,
    ): Response<List<CurrentConditionsJson>>

    @GET("forecasts/v1/hourly/12hour/{locationId}")
    suspend fun get12HourForecast(
        @Path("locationId") locationId: String,
        @Query("metric") metric: Boolean,
    ): Response<List<HourlyForecastJsonResponse>>

    @GET("forecasts/v1/daily/5day/{locationId}")
    suspend fun get5DayForecast(
        @Path("locationId") locationId: String,
        @Query("metric") metric: Boolean,
    ): Response<DailyForecastJsonResponse>

}