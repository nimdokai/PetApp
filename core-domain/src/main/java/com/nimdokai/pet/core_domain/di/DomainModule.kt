package com.nimdokai.pet.core_domain.di

import com.nimdokai.pet.core_domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindGetPetCategoriesUseCase(impl: GetCatCategoriesUseCase): GetPetCategoriesUseCase

    @Binds
    fun bindGetCatCategoryFeedUseCase(impl: GetCatCategoryFeedUseCase): GetPetCategoryFeedUseCase

    @Binds
    fun bindGetCatDetailsUseCase(impl: GetCatDetailsUseCase): GetPetDetailsUseCase

    @Binds
    fun bindGetCurrentConditionsUseCase(impl: GetCurrentConditionsUseCaseImpl): GetCurrentConditionsUseCase

}