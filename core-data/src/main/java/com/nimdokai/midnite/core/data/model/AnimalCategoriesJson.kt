package com.nimdokai.midnite.core.data.model

import com.google.gson.annotations.SerializedName

data class AnimalCategoryJson(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)