package com.nimdokai.pet.core_network.di

import android.content.Context
import com.nimdokai.pet.core_network.ApiConstants
import com.nimdokai.pet.core_network.api.PetApi
import com.nimdokai.pet.core_network.interceptors.ApiKeyInterceptor
import com.nimdokai.pet.core_network.interceptors.ForceCacheInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
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

    @Singleton
    @Binds
    @CacheInterceptor
    fun bindForceCacheInterceptor(impl: ForceCacheInterceptor): Interceptor
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleProvider {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideHttpClientAuthenticated(
        @CatApiKey catApiKey: Interceptor,
        @LoggingOkHttpInterceptor logging: Interceptor,
        @CacheInterceptor cacheInterceptor: Interceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(catApiKey)
            .addInterceptor(logging)
            .addInterceptor(cacheInterceptor)
            .cache(cache)
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
@Qualifier
internal annotation class CacheInterceptor

private const val CACHE_SIZE = 20L * 1024 * 1024 // 20 MB