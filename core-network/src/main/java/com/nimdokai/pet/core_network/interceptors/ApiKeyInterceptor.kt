package com.nimdokai.pet.core_network.interceptors

import com.nimdokai.core_util.Configurable
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(private val configs: Configurable) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val urlWithToken = chain.request().url
            .newBuilder()
            .addQueryParameter("apikey", configs.getAccuWeatherApiToken())
            .build()

        val requestWithToken = chain.request()
            .newBuilder()
            .url(urlWithToken)
            .build()

        return chain.proceed(requestWithToken)
    }

}