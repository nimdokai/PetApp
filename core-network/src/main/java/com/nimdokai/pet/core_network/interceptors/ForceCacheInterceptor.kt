package com.nimdokai.pet.core_network.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class ForceCacheInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()

        if (!isOnline()) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }

        return chain.proceed(builder.build())
    }

    private fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }
}



