package com.nimdokai.core_util.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.nimdokai.pet.core.resources.R

fun <T : Parcelable> SavedStateHandle.getArgs(): T {
    return get<T>(KEY_ARGS)!!
}

fun <T : Parcelable> NavController.open(
    @IdRes destination: Int,
    args: T,
    navOptions: NavOptions = defaultNavOptions,
) {
    val bundle = Bundle()
    bundle.putParcelable(KEY_ARGS, args)
    navigate(destination, bundle, navOptions)
}

private const val KEY_ARGS = "KEY_ARGS"

private val defaultNavOptions: NavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()
