package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class CurrentConditionsJsonResponse(
    @SerializedName("ApparentTemperature")
    val apparentTemperature: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Ceiling")
    val ceiling: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("CloudCover")
    val cloudCover: Int,
    @SerializedName("DewPoint")
    val dewPoint: AvailableUnitTypesHolderJsonResponse,
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
    val past24HourTemperatureDeparture: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Precip1hr")
    val precip1hr: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("PrecipitationSummary")
    val precipitationSummary: PrecipitationSummary,
    @SerializedName("PrecipitationType")
    val precipitationType: Any,
    @SerializedName("Pressure")
    val pressure: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("PressureTendency")
    val pressureTendency: PressureTendency,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("RealFeelTemperatureShade")
    val realFeelTemperatureShade: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("RelativeHumidity")
    val relativeHumidity: Int,
    @SerializedName("Temperature")
    val temperature: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("TemperatureSummary")
    val temperatureSummary: TemperatureSummary,
    @SerializedName("UVIndex")
    val uVIndex: Int,
    @SerializedName("UVIndexText")
    val uVIndexText: String,
    @SerializedName("Visibility")
    val visibility: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("WeatherText")
    val weatherText: String,
    @SerializedName("WetBulbTemperature")
    val wetBulbTemperature: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Wind")
    val wind: Wind,
    @SerializedName("WindChillTemperature")
    val windChillTemperature: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("WindGust")
    val windGust: WindGust
)

data class PrecipitationSummary(
    @SerializedName("Past12Hours")
    val past12Hours: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Past18Hours")
    val past18Hours: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Past24Hours")
    val past24Hours: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Past3Hours")
    val past3Hours: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Past6Hours")
    val past6Hours: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Past9Hours")
    val past9Hours: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("PastHour")
    val pastHour: AvailableUnitTypesHolderJsonResponse,
    @SerializedName("Precipitation")
    val precipitation: MeasureUnitJsonResponse
)

data class PressureTendency(
    @SerializedName("Code")
    val code: String,
    @SerializedName("LocalizedText")
    val localizedText: String
)

data class TemperatureSummary(
    @SerializedName("Past12HourRange")
    val past12HourRange: PastHoursRange,
    @SerializedName("Past24HourRange")
    val past24HourRange: PastHoursRange,
    @SerializedName("Past6HourRange")
    val past6HourRange: PastHoursRange
)

data class Wind(
    @SerializedName("Direction")
    val direction: Direction,
    @SerializedName("Speed")
    val speed: MeasureUnitJsonResponse
)

data class WindGust(
    @SerializedName("Speed")
    val speed: MeasureUnitJsonResponse
)

data class PastHoursRange(
    @SerializedName("Maximum")
    val maximum: MeasureUnitJsonResponse,
    @SerializedName("Minimum")
    val minimum: MeasureUnitJsonResponse
)

data class Direction(
    @SerializedName("Degrees")
    val degrees: Int,
    @SerializedName("English")
    val english: String,
    @SerializedName("Localized")
    val localized: String
)