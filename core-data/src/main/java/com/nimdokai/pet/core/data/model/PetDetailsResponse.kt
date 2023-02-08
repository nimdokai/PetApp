package com.nimdokai.pet.core.data.model

data class PetDetailsResponse(
    val id: String,
    val url: String,
)

data class BreedDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val wikiUrl: String,
)