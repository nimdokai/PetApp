package com.nimdokai.pet.feature.categories.ui

import com.nimdokai.pet.core.data.model.PetCategoryResponse

data class PetCategoryItemUI(
    val id: Int,
    val name: String,
)

data class TeamUI(
    val name: String,
    val imageUrl: String
)

internal fun PetCategoryResponse.toUI() = PetCategoryItemUI(id, name)
