package com.nimdokai.core_util

import com.nimdokai.pet.core.util.BuildConfig
import javax.inject.Inject

interface Configurable {

    fun getCatApiToken(): String

}

class BuildConfigWrapper @Inject constructor(): Configurable {

    override fun getCatApiToken(): String = BuildConfig.CAT_API_KEY

}