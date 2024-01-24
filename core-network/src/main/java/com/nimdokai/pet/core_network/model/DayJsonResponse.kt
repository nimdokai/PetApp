package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class DayJsonResponse(
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String,
    @SerializedName("PrecipitationType")
    val precipitationType: String
)