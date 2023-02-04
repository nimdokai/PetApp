package com.nimdokai.pet.core_network.model

import com.google.gson.annotations.SerializedName

data class PetCategoryJson(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)