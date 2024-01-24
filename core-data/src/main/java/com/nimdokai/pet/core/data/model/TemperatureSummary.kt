package com.nimdokai.pet.core.data.model

data class TemperatureSummary(
    val current: Temperature,
    val pastMin: Temperature,
    val pastMax: Temperature
)