package com.nimdokai.pet.core_domain.model

data class PetImage(
    val id: String,
    val imageUrl: String,
    val categoriesIDs: List<Int>,
    val height: Int,
    val width: Int,
)
