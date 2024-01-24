package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class CurrentConditionsJson(
    @SerializedName("ApparentTemperature")
    val apparentTemperature: AvailableUnitTypesHolderJson,
    @SerializedName("Ceiling")
    val ceiling: AvailableUnitTypesHolderJson,
    @SerializedName("CloudCover")
    val cloudCover: Int,
    @SerializedName("DewPoint")
    val dewPoint: AvailableUnitTypesHolderJson,
    @SerializedName("EpochTime")
    val epochTime: Int,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("IndoorRelativeHumidity")
    val indoorRelativeHumidity: Int,
    @SerializedName("IsDayTime")
    val isDayTime: Boolean,
    @SerializedName("Link")
    val link: String,
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("ObstructionsToVisibility")
    val obstructionsToVisibility: String,
    @SerializedName("Past24HourTemperatureDeparture")
    val past24HourTemperatureDeparture: AvailableUnitTypesHolderJson,
    @SerializedName("Precip1hr")
    val precip1hr: AvailableUnitTypesHolderJson,
    @SerializedName("PrecipitationSummary")
    val precipitationSummary: PrecipitationSummaryJson,
    @SerializedName("PrecipitationType")
    val precipitationType: Any,
    @SerializedName("Pressure")
    val pressure: AvailableUnitTypesHolderJson,
    @SerializedName("PressureTendency")
    val pressureTendency: PressureTendencyJson,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature: AvailableUnitTypesHolderJson,
    @SerializedName("RealFeelTemperatureShade")
    val realFeelTemperatureShade: AvailableUnitTypesHolderJson,
    @SerializedName("RelativeHumidity")
    val relativeHumidity: Int,
    @SerializedName("Temperature")
    val temperature: AvailableUnitTypesHolderJson,
    @SerializedName("TemperatureSummary")
    val temperatureSummary: TemperatureSummaryJson,
    @SerializedName("UVIndex")
    val uVIndex: Int,
    @SerializedName("UVIndexText")
    val uVIndexText: String,
    @SerializedName("Visibility")
    val visibility: AvailableUnitTypesHolderJson,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("WeatherText")
    val weatherText: String,
    @SerializedName("WetBulbTemperature")
    val wetBulbTemperature: AvailableUnitTypesHolderJson,
    @SerializedName("Wind")
    val wind: WindJson,
    @SerializedName("WindChillTemperature")
    val windChillTemperature: AvailableUnitTypesHolderJson,
    @SerializedName("WindGust")
    val windGust: WindGust
)

data class PrecipitationSummaryJson(
    @SerializedName("Past12Hours")
    val past12Hours: AvailableUnitTypesHolderJson,
    @SerializedName("Past18Hours")
    val past18Hours: AvailableUnitTypesHolderJson,
    @SerializedName("Past24Hours")
    val past24Hours: AvailableUnitTypesHolderJson,
    @SerializedName("Past3Hours")
    val past3Hours: AvailableUnitTypesHolderJson,
    @SerializedName("Past6Hours")
    val past6Hours: AvailableUnitTypesHolderJson,
    @SerializedName("Past9Hours")
    val past9Hours: AvailableUnitTypesHolderJson,
    @SerializedName("PastHour")
    val pastHour: AvailableUnitTypesHolderJson,
    @SerializedName("Precipitation")
    val precipitation: AvailableUnitTypesHolderJson
)

data class PressureTendencyJson(
    @SerializedName("Code")
    val code: String,
    @SerializedName("LocalizedText")
    val localizedText: String
)

data class TemperatureSummaryJson(
    @SerializedName("Past12HourRange")
    val past12HourRange: PastHoursRangeJson,
    @SerializedName("Past24HourRange")
    val past24HourRange: PastHoursRangeJson,
    @SerializedName("Past6HourRange")
    val past6HourRange: PastHoursRangeJson
)

data class WindJson(
    @SerializedName("Direction")
    val direction: DirectionJson,
    @SerializedName("Speed")
    val speed: AvailableUnitTypesHolderJson
)

data class WindGust(
    @SerializedName("Speed")
    val speed: AvailableUnitTypesHolderJson
)

data class PastHoursRangeJson(
    @SerializedName("Maximum")
    val maximum: AvailableUnitTypesHolderJson,
    @SerializedName("Minimum")
    val minimum: AvailableUnitTypesHolderJson
)

data class DirectionJson(
    @SerializedName("Degrees")
    val degrees: Int,
    @SerializedName("English")
    val english: String,
    @SerializedName("Localized")
    val localized: String
)