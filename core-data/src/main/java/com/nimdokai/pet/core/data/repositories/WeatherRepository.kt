package com.nimdokai.pet.core.data.repositories

import com.nimdokai.pet.core.data.DataResponse
import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.data.model.Temperature
import com.nimdokai.pet.core.data.tryCall
import com.nimdokai.pet.core_network.api.AccuWeatherApi
import com.nimdokai.pet.core_network.model.CurrentConditionsJsonResponse
import com.nimdokai.pet.core_network.model.TemperatureHolderJsonResponse
import javax.inject.Inject

interface WeatherRepository {

    suspend fun getCurrentConditions(locationId: String, unit: String): DataResponse<out CurrentConditions>
}

class WeatherRepositoryImpl @Inject constructor(
    private val accuWeatherApi: AccuWeatherApi
) : WeatherRepository {

    override suspend fun getCurrentConditions(locationId: String, unit: String): DataResponse<out CurrentConditions> {
        return tryCall(
            { accuWeatherApi.getCurrentConditionsForLocation(locationId) }
        ) {
            it.first().toDataModel(unit)
        }
    }

    private fun CurrentConditionsJsonResponse.toDataModel(unit: String): CurrentConditions {
        return CurrentConditions(
            epochTime = epochTime,
            hasPrecipitation = hasPrecipitation,
            isDayTime = isDayTime,
            temperature = temperatureHolder.toDataModel(unit)
        )
    }

    private fun TemperatureHolderJsonResponse.toDataModel(unit: String): Temperature {
        return when(unit) {
            "F" -> Temperature.Fahrenheit(imperial.value)
            else -> Temperature.Celsius(metric.value)
        }
    }

}