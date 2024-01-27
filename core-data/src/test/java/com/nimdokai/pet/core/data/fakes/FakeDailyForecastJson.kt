package com.nimdokai.pet.core.data.fakes

import com.nimdokai.pet.core_network.model.DailyForecastItemJsonResponse
import com.nimdokai.pet.core_network.model.DailyForecastJsonResponse
import com.nimdokai.pet.core_network.model.DayJsonResponse
import com.nimdokai.pet.core_network.model.HeadlineJsonResponse
import com.nimdokai.pet.core_network.model.MeasureUnitJson
import com.nimdokai.pet.core_network.model.TemperatureMaxMinHolderJsonResponse

val fakeHeadlineJsonResponse: HeadlineJsonResponse = HeadlineJsonResponse(
    category = "category",
    effectiveDate = "effectiveDate",
    effectiveEpochDate = 1,
    endDate = "endDate",
    endEpochDate = 1,
    link = "link",
    mobileLink = "mobileLink",
    severity = 1,
    text = "text"
)

val fakeDailyForecastItemJsonResponse = DailyForecastItemJsonResponse(
    date = "date",
    epochDate = 1,
    day = DayJsonResponse(
        icon = 1,
        hasPrecipitation = true,
        precipitationIntensity = "precipitationIntensity",
        precipitationType = "precipitationType"
    ),
    night = DayJsonResponse(
        icon = 1,
        hasPrecipitation = true,
        precipitationIntensity = "nightPrecipitationIntensity",
        precipitationType = "nightPrecipitationType"
    ),
    temperatureMaxMinHolderJsonResponse = TemperatureMaxMinHolderJsonResponse(
        maximum = MeasureUnitJson(
            unit = "unit",
            unitType = 1,
            value = 1.0
        ),
        minimum = MeasureUnitJson(
            unit = "unit",
            unitType = 1,
            value = 1.0
        )
    )
)
val fakeDailyForecastJson = DailyForecastJsonResponse(
    dailyForecasts = listOf(fakeDailyForecastItemJsonResponse),
    headline = fakeHeadlineJsonResponse
)