package com.nimdokai.pet.core_network.interceptors

import com.nimdokai.core_util.Configurable
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(private val configs: Configurable) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(configs.getCatApiToken(), "x-api-key")
            .build()

        return chain.proceed(request)
    }

}