package com.nimdokai.pet.core.data.fakes

import com.nimdokai.pet.core_network.model.HourlyForecastJsonResponse
import com.nimdokai.pet.core_network.model.MeasureUnitJson

internal val fakeHourlyForecastJson: HourlyForecastJsonResponse = HourlyForecastJsonResponse(
    epochTime = 1619450400,
    hasPrecipitation = false,
    temperature = MeasureUnitJson(
        value = 10.0,
        unit = "C",
        unitType = 17
    ),
    weatherIcon = 1,
    dateTime = "2021-04-26T10:00:00+02:00",
    iconPhrase = "Sunny",
    isDaylight = true,
    link = "link",
    mobileLink = "mobileLink",
    precipitationProbability = 0,
)