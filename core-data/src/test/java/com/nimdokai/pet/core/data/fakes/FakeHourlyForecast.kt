package com.nimdokai.pet.core.data.fakes

import com.nimdokai.pet.core.data.model.HourlyForecast
import com.nimdokai.pet.core.data.model.Temperature

val fakeHourlyForecast = HourlyForecast(
    epochTime = fakeHourlyForecastJson.epochTime,
    hasPrecipitation = fakeHourlyForecastJson.hasPrecipitation,
    temperature = Temperature.Celsius(
        value = fakeHourlyForecastJson.temperature.value
    ),
    weatherType = fakeHourlyForecastJson.weatherIcon,
)