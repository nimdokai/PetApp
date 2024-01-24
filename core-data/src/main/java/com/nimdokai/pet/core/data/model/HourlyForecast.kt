package com.nimdokai.pet.core.data.model

data class HourlyForecast(
    val epochTime: Int,
    val hasPrecipitation: Boolean,
    val temperature: Temperature,
)