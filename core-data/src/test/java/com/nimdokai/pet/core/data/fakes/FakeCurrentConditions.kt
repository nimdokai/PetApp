package com.nimdokai.pet.core.data.fakes

import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.data.model.Temperature
import com.nimdokai.pet.core.data.model.TemperatureSummary

val fakeCurrentConditions = CurrentConditions(
    epochTime = fakeCurrentConditionsJson.epochTime,
    hasPrecipitation = fakeCurrentConditionsJson.hasPrecipitation,
    isDayTime = fakeCurrentConditionsJson.isDayTime,
    description = fakeCurrentConditionsJson.weatherText,
    weatherType = fakeCurrentConditionsJson.weatherIcon,
    temperatureSummary = TemperatureSummary(
        current = Temperature.Celsius(
            value = fakeCurrentConditionsJson.temperature.metric.value
        ),
        pastMin = Temperature.Celsius(
            value = fakeCurrentConditionsJson.temperatureSummary.past24HourRange.minimum.metric.value,
        ),
        pastMax = Temperature.Celsius(
            value = fakeCurrentConditionsJson.temperatureSummary.past24HourRange.maximum.metric.value,
        )
    )
)