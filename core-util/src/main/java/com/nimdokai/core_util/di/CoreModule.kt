package com.nimdokai.core_util.di

import com.nimdokai.core_util.BuildConfigWrapper
import com.nimdokai.core_util.Configurable
import com.nimdokai.core_util.date.DateFormatter
import com.nimdokai.core_util.date.DateFormatterDefault
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
        return DateFormatterDefault
    }

}

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreModuleBinder {

    @Binds
    @Singleton
    fun bindConfigurable(implementation: BuildConfigWrapper): Configurable

}
