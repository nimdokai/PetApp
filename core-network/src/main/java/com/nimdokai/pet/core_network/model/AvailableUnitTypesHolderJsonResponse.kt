package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class AvailableUnitTypesHolderJsonResponse(
    @SerializedName("Imperial")
    val imperial: MeasureUnitJsonResponse,
    @SerializedName("Metric")
    val metric: MeasureUnitJsonResponse
)