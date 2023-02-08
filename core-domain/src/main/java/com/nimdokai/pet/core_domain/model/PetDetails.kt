package com.nimdokai.pet.core_domain.model

data class PetDetails(
    val id: String,
    val url: String,
)

data class Breed(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val wikiUrl: String = ""
)