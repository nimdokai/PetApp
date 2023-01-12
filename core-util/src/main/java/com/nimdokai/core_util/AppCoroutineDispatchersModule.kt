package com.nimdokai.core_util

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
public object AppCoroutineDispatchersModule {

    @Provides
    public fun providesDispatchers(): AppCoroutineDispatchers = DefaultAppCoroutineDispatchers
}