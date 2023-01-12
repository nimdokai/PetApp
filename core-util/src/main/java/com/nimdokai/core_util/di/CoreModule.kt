package com.nimdokai.core_util.di

import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.core_util.navigation.date.DefaultDateFormatter
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