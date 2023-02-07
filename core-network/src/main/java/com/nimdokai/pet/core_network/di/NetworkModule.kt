package com.nimdokai.pet.core_network.di

import com.nimdokai.pet.core_network.ApiConstants
import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.interceptors.ApiKeyInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModuleBinder {

    @Singleton
    @Binds
    @CatApiKey
    fun bindApiKeyInterceptor(impl: ApiKeyInterceptor): Interceptor
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleProvider {

    @Provides
    @Singleton
    fun provideHttpClientAuthenticated(
        @CatApiKey catApiKey: Interceptor,
        @LoggingOkHttpInterceptor logging: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(catApiKey)
            .addInterceptor(logging)
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
    fun providePetApi(retrofit: Retrofit): PetApi = retrofit.create((PetApi::class.java))

    @LoggingOkHttpInterceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

}

@Qualifier
internal annotation class CatApiKey
@Qualifier
internal annotation class LoggingOkHttpInterceptor