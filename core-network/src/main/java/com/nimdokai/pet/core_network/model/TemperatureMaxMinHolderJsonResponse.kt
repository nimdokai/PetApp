package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class TemperatureMaxMinHolderJsonResponse(
    @SerializedName("Maximum")
    val maximum: TemperatureJsonResponse,
    @SerializedName("Minimum")
    val minimum: TemperatureJsonResponse
)