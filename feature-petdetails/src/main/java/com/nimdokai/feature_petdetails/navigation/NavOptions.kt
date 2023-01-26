package com.nimdokai.feature_petdetails.navigation

import androidx.navigation.NavOptions
import com.nimdokai.pet.core.resources.R

val defaultNavOptions: NavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()
