package com.nimdokai.pet.core.testing.fakes

import com.nimdokai.pet.core_domain.DomainResult
import com.nimdokai.pet.core_domain.GetPetCategoriesUseCase
import com.nimdokai.pet.core_domain.model.PetCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class FakeGetPetCategoriesUseCase : GetPetCategoriesUseCase {

    lateinit var domainResult: Flow<DomainResult<out List<PetCategory>>>

    override suspend fun invoke(): Flow<DomainResult<out List<PetCategory>>> {
        return domainResult.onEach { delay(1000) }
    }
}