package com.nimdokai.pet.core_network.model

import com.google.gson.annotations.SerializedName

data class PetImageJson(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
) {
    data class Category(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
    )
}