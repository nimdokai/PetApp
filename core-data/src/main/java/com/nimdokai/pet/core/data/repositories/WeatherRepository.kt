package com.nimdokai.pet.core.data.repositories

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.data.model.DailyForecast
import com.nimdokai.pet.core.data.model.Temperature
import com.nimdokai.pet.core.data.model.HourlyForecast
import com.nimdokai.pet.core.data.tryCall
import com.nimdokai.pet.core_network.api.AccuWeatherApi
import com.nimdokai.pet.core_network.model.CurrentConditionsJsonResponse
import com.nimdokai.pet.core_network.model.DailyForecastItemJsonResponse
import com.nimdokai.pet.core_network.model.HourlyForecastJsonResponse
import com.nimdokai.pet.core_network.model.AvailableUnitTypesHolderJsonResponse
import javax.inject.Inject

interface WeatherRepository {

    suspend fun getCurrentConditions(
        locationId: String,
        temperatureUnit: String
    ): DataResponse<out CurrentConditions>

    suspend fun get12HourForecast(
        locationId: String,
        useMetric: Boolean
    ): DataResponse<out List<HourlyForecast>>

    suspend fun get5DayForecast(
        locationId: String,
        useMetric: Boolean
    ): DataResponse<out List<DailyForecast>>
}

class WeatherRepositoryImpl @Inject constructor(
    private val accuWeatherApi: AccuWeatherApi
) : WeatherRepository {

    override suspend fun getCurrentConditions(
        locationId: String,
        temperatureUnit: String
    ): DataResponse<out CurrentConditions> {
        return tryCall(
            { accuWeatherApi.getCurrentConditionsForLocation(locationId) }
        ) {
            // this contain single element array
            it.first().toDataModel(temperatureUnit)
        }
    }

    override suspend fun get12HourForecast(
        locationId: String,
        useMetric: Boolean
    ): DataResponse<out List<HourlyForecast>> {
        return tryCall(
            { accuWeatherApi.get12HourForecast(locationId, useMetric) }
        ) { hourlyForecastJsonResponse ->
            hourlyForecastJsonResponse.map { it.toDataModel(useMetric) }
        }
    }

    override suspend fun get5DayForecast(
        locationId: String,
        useMetric: Boolean
    ): DataResponse<out List<DailyForecast>> {
        return tryCall(
            { accuWeatherApi.get5DayForecast(locationId, useMetric) }
        ) { dailyForecastJsonResponse ->
            dailyForecastJsonResponse.dailyForecasts.map { it.toDataModel(useMetric) }
        }
    }

    private fun CurrentConditionsJsonResponse.toDataModel(unit: String): CurrentConditions {
        return CurrentConditions(
            epochTime = epochTime,
            hasPrecipitation = hasPrecipitation,
            isDayTime = isDayTime,
            temperature = temperature.toDataModel(unit)
        )
    }

    private fun AvailableUnitTypesHolderJsonResponse.toDataModel(unit: String): Temperature {
        return when (unit) {
            "F" -> Temperature.Fahrenheit(imperial.value)
            else -> Temperature.Celsius(metric.value)
        }
    }

    private fun DailyForecastItemJsonResponse.toDataModel(useMetric: Boolean): DailyForecast {

        val minTempValue = temperatureMaxMinHolderJsonResponse.minimum.value
        val nightTemperature = if (useMetric) {
            Temperature.Celsius(minTempValue)
        } else {
            Temperature.Fahrenheit(minTempValue)
        }

        val maxTempValue = temperatureMaxMinHolderJsonResponse.maximum.value
        val dayTemperature = if (useMetric) {
            Temperature.Celsius(maxTempValue)
        } else {
            Temperature.Fahrenheit(maxTempValue)
        }

        return DailyForecast(
            epochDate,
            dayTemperature,
            nightTemperature,
        )
    }

}

private fun HourlyForecastJsonResponse.toDataModel(useMetric: Boolean): HourlyForecast {
    return HourlyForecast(
        epochTime = epochTime,
        hasPrecipitation = hasPrecipitation,
        temperature = if (useMetric) {
            Temperature.Celsius(temperature.value)
        } else {
            Temperature.Fahrenheit(temperature.value)
        }
    )

}