package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class TemperatureHolderJsonResponse(
    @SerializedName("Imperial")
    val imperial: TemperatureJsonResponse,
    @SerializedName("Metric")
    val metric: TemperatureJsonResponse
)