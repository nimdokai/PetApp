package com.nimdokai.core_util.di

import com.nimdokai.core_util.BuildConfigWrapper
import com.nimdokai.core_util.Configurable
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.core_util.navigation.date.DefaultDateFormatter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideDateFormatter(): DateFormatter {
        return DefaultDateFormatter
    }

}

@Module
@InstallIn(SingletonComponent::class)
interface CoreModuleBinder {

    @Binds
    @Singleton
    fun bindConfigurable(implementation: BuildConfigWrapper): Configurable

}