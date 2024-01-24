package com.nimdokai.core_util

import com.nimdokai.pet.core.util.BuildConfig
import javax.inject.Inject

interface Configurable {

    fun getAccuWeatherApiToken(): String

}

class BuildConfigWrapper @Inject constructor(): Configurable {

    override fun getAccuWeatherApiToken(): String = BuildConfig.ACCU_WEATHER_API_KEY

}