package com.nimdokai.pet.core.data.fakes

import com.nimdokai.pet.core_network.api.AccuWeatherApi
import com.nimdokai.pet.core_network.model.CurrentConditionsJson
import com.nimdokai.pet.core_network.model.DailyForecastJsonResponse
import com.nimdokai.pet.core_network.model.HourlyForecastJsonResponse
import retrofit2.Response
import java.io.IOException

class FakeWeatherApi : AccuWeatherApi {

    internal var exception: IOException? = null
    internal lateinit var hourlyForecastResponse: Response<List<HourlyForecastJsonResponse>>
    internal lateinit var currentConditionsResponse: Response<List<CurrentConditionsJson>>
    internal lateinit var dailyForecastResponse: Response<DailyForecastJsonResponse>

    override suspend fun getCurrentConditionsForLocation(
        locationId: String,
        details: Boolean
    ): Response<List<CurrentConditionsJson>> {
        exception?.let { throw it }
        return currentConditionsResponse
    }

    override suspend fun get12HourForecast(
        locationId: String,
        metric: Boolean
    ): Response<List<HourlyForecastJsonResponse>> {
        exception?.let { throw it }
        return hourlyForecastResponse
    }

    override suspend fun get5DayForecast(locationId: String, metric: Boolean): Response<DailyForecastJsonResponse> {
        exception?.let { throw it }
        return dailyForecastResponse
    }
}