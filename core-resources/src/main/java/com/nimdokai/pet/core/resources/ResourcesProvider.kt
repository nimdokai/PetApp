package com.nimdokai.pet.core.resources

import android.content.res.Resources
import androidx.annotation.StringRes

interface ResourcesProvider {
    fun getString(@StringRes id: Int, vararg formatArgs: Any = emptyArray()) : String
}

class ResourcesProviderImpl(private val resources: Resources) : ResourcesProvider {

    override fun getString(id: Int, vararg formatArgs: Any): String {
        return resources.getString(id, formatArgs)
    }

}