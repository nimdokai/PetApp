package com.nimdokai.pet.core.data.model

data class PetImageResponse(
    val id: String,
    val imageUrl: String,
    val categoriesIDs: List<Int>,
)