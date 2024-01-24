package com.nimdokai.pet.core.data.repositories

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.data.model.DailyForecast
import com.nimdokai.pet.core.data.model.HourlyForecast
import com.nimdokai.pet.core.data.model.Temperature
import com.nimdokai.pet.core.data.model.TemperatureSummary
import com.nimdokai.pet.core.data.tryCall
import com.nimdokai.pet.core_network.api.AccuWeatherApi
import com.nimdokai.pet.core_network.model.AvailableUnitTypesHolderJson
import com.nimdokai.pet.core_network.model.CurrentConditionsJson
import com.nimdokai.pet.core_network.model.DailyForecastItemJsonResponse
import com.nimdokai.pet.core_network.model.HourlyForecastJsonResponse
import com.nimdokai.pet.core_network.model.TemperatureSummaryJson
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

    private fun CurrentConditionsJson.toDataModel(unit: String): CurrentConditions {
        return CurrentConditions(
            epochTime = epochTime,
            hasPrecipitation = hasPrecipitation,
            isDayTime = isDayTime,
            temperatureSummary = temperatureSummary.toDataModel(temperature, unit),
            description = weatherText,
            weatherType = weatherIcon,
        )
    }

}

internal fun AvailableUnitTypesHolderJson.toDataModel(unit: String): Temperature {
    //ToDo this is only working for temperature
    return when (unit) {
        "F" -> Temperature.Fahrenheit(imperial.value)
        else -> Temperature.Celsius(metric.value)
    }
}

internal fun TemperatureSummaryJson.toDataModel(
    currentTemp: AvailableUnitTypesHolderJson,
    measureUnit: String
): TemperatureSummary {
    return TemperatureSummary(
        current = currentTemp.toDataModel(measureUnit),
        pastMin = past24HourRange.minimum.toDataModel(measureUnit),
        pastMax = past24HourRange.maximum.toDataModel(measureUnit),
    )
}


internal fun HourlyForecastJsonResponse.toDataModel(useMetric: Boolean): HourlyForecast {
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

internal fun DailyForecastItemJsonResponse.toDataModel(useMetric: Boolean): DailyForecast {
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