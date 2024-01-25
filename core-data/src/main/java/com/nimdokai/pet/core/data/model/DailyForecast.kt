package com.nimdokai.pet.core.data.model

data class DailyForecast(
    val epochDate: Int,
    val dayTemperature: Temperature,
    val nightTemperature: Temperature,
    val weatherType: Int,
)