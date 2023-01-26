package com.nimdokai.pet.core.data.di

import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core.data.ApiConstants
import com.nimdokai.pet.core.data.CatRepository
import com.nimdokai.pet.core.data.api.PetApi
import com.nimdokai.pet.core.data.interceptors.ApiKeyInterceptor
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
    fun bindPetRepository(impl: CatRepository): PetRepository

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
    fun providePetApi(retrofit: Retrofit): PetApi = retrofit.create(PetApi::class.java)
}

@Qualifier
internal annotation class CatApiKey
