package com.nimdokai.core_util.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PermissionChecker {

    fun hasPermission(permission: String): Boolean
}

class PermissionCheckerImpl @Inject constructor(@ApplicationContext private val context: Context) : PermissionChecker {

    override fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}