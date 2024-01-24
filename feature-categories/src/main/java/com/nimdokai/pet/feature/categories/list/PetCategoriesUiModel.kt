package com.nimdokai.pet.feature.categories.list

import androidx.annotation.DrawableRes

data class CurrentWeatherUi(
    val epochTime: Int,
    val hasPrecipitation: Boolean,
    val isDayTime: Boolean,
    val temperature: String,
    val pastMaxTemp: String,
    val pastMinTemp: String,
    @DrawableRes val icon: Int,
    val description: String,
)

val emptyCurrentWeatherUiState = CurrentWeatherUi(
    epochTime = 0,
    hasPrecipitation = false,
    isDayTime = false,
    temperature = "",
    pastMaxTemp = "",
    pastMinTemp = "",
    icon = 0,
    description = "",
)