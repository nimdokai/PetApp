package com.nimdokai.pet.core_network.di

import android.content.Context
import com.nimdokai.pet.core_network.ApiConstants
import com.nimdokai.pet.core_network.api.AccuWeatherApi
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
    @WeatherApiKey
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
        @WeatherApiKey apiKey: Interceptor,
        @LoggingOkHttpInterceptor logging: Interceptor,
        @CacheInterceptor cacheInterceptor: Interceptor,
        cache: Cache,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKey)
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
    fun providesWeatherApi(retrofit: Retrofit): AccuWeatherApi = retrofit.create((AccuWeatherApi::class.java))

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
internal annotation class WeatherApiKey
@Qualifier
internal annotation class LoggingOkHttpInterceptor
@Qualifier
internal annotation class CacheInterceptor

private const val CACHE_SIZE = 5L * 1024 * 1024 // 5 MB