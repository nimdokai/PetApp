package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class AvailableUnitTypesHolderJson(
    @SerializedName("Imperial")
    val imperial: MeasureUnitJson,
    @SerializedName("Metric")
    val metric: MeasureUnitJson
)