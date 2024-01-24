package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class TemperatureJsonResponse(
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int,
    @SerializedName("Value")
    val value: Double
)