package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class HourlyForecastJsonResponse(
    @SerializedName("DateTime")
    val dateTime: String,
    @SerializedName("EpochDateTime")
    val epochTime: Int,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("IconPhrase")
    val iconPhrase: String,
    @SerializedName("IsDaylight")
    val isDaylight: Boolean,
    @SerializedName("Link")
    val link: String,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("PrecipitationProbability")
    val precipitationProbability: Int,
    @SerializedName("Temperature")
    val temperature: MeasureUnitJson,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int
)