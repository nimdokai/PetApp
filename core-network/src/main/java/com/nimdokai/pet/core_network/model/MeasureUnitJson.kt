package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class MeasureUnitJson(
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int,
    @SerializedName("Value")
    val value: Double
)