package com.nimdokai.pet.feature.categories.list

data class CurrentWeatherUi(
    val epochTime: Int,
    val hasPrecipitation: Boolean,
    val isDayTime: Boolean,
    val temperature: String,
)