package com.nimdokai.feature_petdetails.di

import com.nimdokai.core_util.navigation.PetDetailsNavigator
import com.nimdokai.feature_petdetails.navigation.PetDetailsNavigatorDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PetDetailsModule {

    @Binds
    internal abstract fun bindPetDetailsNavigator(navigator: PetDetailsNavigatorDefault): PetDetailsNavigator
}