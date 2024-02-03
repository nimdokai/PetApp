package com.nimdokai.pet.di

import android.content.Context
import com.nimdokai.pet.core.resources.ResourcesProvider
import com.nimdokai.pet.core.resources.ResourcesProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideResourcesProvider(@ActivityContext activity: Context): ResourcesProvider {
        return ResourcesProviderImpl(activity.resources)
    }

}