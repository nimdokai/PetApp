package com.nimdokai.feature_petdetails.ui

data class PetDetailsUI(
    val id: String,
    val url: String,
//    val breed: BreedUI,
)

data class BreedUI(
    val id: String,
    val name: String,
    val description: String,
    val wikiUrl: String
)