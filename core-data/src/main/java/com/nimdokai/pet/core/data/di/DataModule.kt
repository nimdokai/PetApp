package com.nimdokai.pet.core.data.di

import com.nimdokai.pet.core.data.CatRepository
import com.nimdokai.pet.core.data.PetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinder {

    @Singleton
    @Binds
    fun bindPetRepository(impl: CatRepository): PetRepository

}