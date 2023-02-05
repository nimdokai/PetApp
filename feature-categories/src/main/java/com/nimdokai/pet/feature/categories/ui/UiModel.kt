package com.nimdokai.pet.feature.categories.ui

import com.nimdokai.pet.core_domain.model.PetCategory

data class PetCategoryItemUI(
    val id: Int,
    val name: String,
    val imageUrl: String,
)

data class TeamUI(
    val name: String,
    val imageUrl: String
)

internal fun PetCategory.toUI() = PetCategoryItemUI(id, name, imageUrl)
