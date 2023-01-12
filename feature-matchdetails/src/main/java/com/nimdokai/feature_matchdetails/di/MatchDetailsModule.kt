package com.nimdokai.feature_matchdetails.di

import com.nimdokai.core_util.navigation.MatchDetailsNavigator
import com.nimdokai.feature_matchdetails.navigation.MatchDetailsNavigatorDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MatchDetailsModule {

    @Binds
    internal abstract fun bindMatchDetailsNavigator(navigator: MatchDetailsNavigatorDefault): MatchDetailsNavigator
}