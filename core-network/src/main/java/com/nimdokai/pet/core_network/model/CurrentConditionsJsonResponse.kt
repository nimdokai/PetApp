package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class CurrentConditionsJsonResponse(
    @SerializedName("EpochTime")
    val epochTime: Int,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("IsDayTime")
    val isDayTime: Boolean,
    @SerializedName("Link")
    val link: String,
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("PrecipitationType")
    val precipitationType: Any?,
    @SerializedName("Temperature")
    val temperatureHolder: TemperatureHolderJsonResponse,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("WeatherText")
    val weatherText: String
)