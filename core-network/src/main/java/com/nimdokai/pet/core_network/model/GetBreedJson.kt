package com.nimdokai.pet.core_network.model


import com.google.gson.annotations.SerializedName

data class GetBreedJson(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String
)