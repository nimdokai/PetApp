package com.nimdokai.pet.core.data.model

import com.nimdokai.pet.core_network.model.TemperatureHolderJsonResponse

data class CurrentConditions(
    val epochTime: Int,
    val hasPrecipitation: Boolean,
    val isDayTime: Boolean,
    val temperature: Temperature,
)