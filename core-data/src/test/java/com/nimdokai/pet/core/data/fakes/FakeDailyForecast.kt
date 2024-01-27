package com.nimdokai.pet.core.data.fakes

import com.nimdokai.pet.core.data.model.DailyForecast
import com.nimdokai.pet.core.data.model.Temperature

val fakeDailyForecast = DailyForecast(
    epochDate = fakeDailyForecastItemJsonResponse.epochDate,
    dayTemperature = Temperature.Celsius(
        value = fakeDailyForecastItemJsonResponse.temperatureMaxMinHolderJsonResponse.maximum.value
    ),
    nightTemperature = Temperature.Celsius(
        value = fakeDailyForecastItemJsonResponse.temperatureMaxMinHolderJsonResponse.minimum.value
    ),
    weatherType = fakeDailyForecastItemJsonResponse.day.icon
)