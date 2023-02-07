package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core_domain.model.PetImage
import kotlinx.coroutines.flow.Flow

interface GetPetCategoryFeedUseCase {
    suspend operator fun invoke(categoryID: String): Flow<DomainResult<out List<PetImage>>>
}
