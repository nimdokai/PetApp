package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class DailyForecastItemJsonResponse(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Day")
    val day: DayJsonResponse,
    @SerializedName("EpochDate")
    val epochDate: Int,
    @SerializedName("Night")
    val night: DayJsonResponse,
    @SerializedName("Temperature")
    val temperatureMaxMinHolderJsonResponse: TemperatureMaxMinHolderJsonResponse
)