package com.nimdokai.midnite.di

import com.nimdokai.midnite.core.data.MatchesRepository
import com.nimdokai.midnite.core.data.di.DataModuleBinder
import com.nimdokai.midnite.data.FakeMatchesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModuleBinder::class]
)
interface TestDataModule {
    @Singleton
    @Binds
    fun bindsDataItemTypeRepository(matchesRepository: FakeMatchesRepository): MatchesRepository
}
