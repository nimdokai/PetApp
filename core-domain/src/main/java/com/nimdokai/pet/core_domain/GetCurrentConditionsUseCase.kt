package com.nimdokai.pet.core_domain

import com.nimdokai.pet.core.data.model.CurrentConditions
import kotlinx.coroutines.flow.Flow

interface GetCurrentConditionsUseCase {

    operator fun invoke(): Flow<DomainResult<out CurrentConditions>>

}