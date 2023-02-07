package com.nimdokai.pet.feature.categories.feed

import com.nimdokai.pet.core_domain.model.PetImage

data class PetCategoryFeedItemUI(
    val id: String,
    val imageUrl: String,
)

internal fun PetImage.toUI() = PetCategoryFeedItemUI(id, imageUrl)
