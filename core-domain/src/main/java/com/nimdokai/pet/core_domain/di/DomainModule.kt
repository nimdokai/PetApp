package com.nimdokai.pet.core_domain.di

import com.nimdokai.pet.core_domain.GetCatCategoriesUseCase
import com.nimdokai.pet.core_domain.GetCatCategoryFeedUseCase
import com.nimdokai.pet.core_domain.GetPetCategoriesUseCase
import com.nimdokai.pet.core_domain.GetPetCategoryFeedUseCase
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

}