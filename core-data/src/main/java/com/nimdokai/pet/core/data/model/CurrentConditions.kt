package com.nimdokai.pet.core.data.model

data class CurrentConditions(
    val epochTime: Int,
    val hasPrecipitation: Boolean,
    val isDayTime: Boolean,
    val temperature: Temperature,
)