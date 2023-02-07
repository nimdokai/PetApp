package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core_domain.model.PetCategory
import kotlinx.coroutines.flow.Flow

interface GetPetCategoriesUseCase {

    suspend operator fun invoke(): Flow<DomainResult<out List<PetCategory>>>

}
