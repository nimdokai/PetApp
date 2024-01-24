package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class DailyForecastJsonResponse(
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecastItemJsonResponse>,
    @SerializedName("Headline")
    val headline: HeadlineJsonResponse
)