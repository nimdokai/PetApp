package com.nimdokai.midnite.feature.matches.ui

import com.nimdokai.midnite.core.data.model.AnimalCategoryResponse

data class AnimalCategoryItemUI(
    val id: Int,
    val name: String,
)

data class TeamUI(
    val name: String,
    val imageUrl: String
)

internal fun AnimalCategoryResponse.toUI() = AnimalCategoryItemUI(id, name)
