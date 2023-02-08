package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core_domain.model.PetDetails
import kotlinx.coroutines.flow.Flow

interface GetPetDetailsUseCase {
    suspend operator fun invoke(catID: String): Flow<DomainResult<out PetDetails>>

}