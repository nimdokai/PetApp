package com.nimdokai.midnite.core.data.di

import com.nimdokai.midnite.core.data.AnimalRepository
import com.nimdokai.midnite.core.data.ApiConstants
import com.nimdokai.midnite.core.data.CatRepository
import com.nimdokai.midnite.core.data.api.MatchesApi
import com.nimdokai.midnite.core.data.interceptors.ApiKeyInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinder {

    @Singleton
    @Binds
    fun bindsDataItemTypeRepository(matchesRepository: CatRepository): AnimalRepository

    @Singleton
    @Binds
    @CatApiKey
    fun bindApiKeyInterceptor(impl: ApiKeyInterceptor): Interceptor
}

@Module
@InstallIn(SingletonComponent::class)
object DataModuleProvider {

    @Provides
    @Singleton
    fun provideHttpClientAuthenticated(
        @CatApiKey catApiKey: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(catApiKey)
            .build()
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

@Qualifier
internal annotation class CatApiKey
