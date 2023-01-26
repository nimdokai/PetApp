package com.nimdokai.pet.core.data.model

import com.google.gson.annotations.SerializedName

data class PetCategoryJson(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)