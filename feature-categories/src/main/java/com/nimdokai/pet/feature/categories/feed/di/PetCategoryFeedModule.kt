package com.nimdokai.pet.feature.categories.feed.di

import com.nimdokai.core_util.navigation.PetCategoryFeedNavigator
import com.nimdokai.pet.feature.categories.feed.navigation.PetCategoryFeedNavigatorDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface PetCategoryFeedModule {

    @Binds
    fun bindPetCategoryFeedNavigator(navigator: PetCategoryFeedNavigatorDefault): PetCategoryFeedNavigator
}