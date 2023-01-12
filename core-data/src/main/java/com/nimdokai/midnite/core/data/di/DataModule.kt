package com.nimdokai.midnite.core.data.di

import com.nimdokai.midnite.core.data.ApiConstants
import com.nimdokai.midnite.core.data.DefaultMatchesRepository
import com.nimdokai.midnite.core.data.MatchesRepository
import com.nimdokai.midnite.core.data.api.MatchesApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinder {

    @Singleton
    @Binds
    fun bindsDataItemTypeRepository(matchesRepository: DefaultMatchesRepository): MatchesRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModuleProvider {

    @Provides
    @Singleton
    fun provideHttpClientAuthenticated(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofitAuthenticated(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMatchesApi(retrofit: Retrofit): MatchesApi = retrofit.create(MatchesApi::class.java)
}