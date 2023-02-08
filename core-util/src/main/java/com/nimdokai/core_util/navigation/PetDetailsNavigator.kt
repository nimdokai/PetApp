package com.nimdokai.core_util.navigation

import android.os.Parcelable
import androidx.navigation.NavController
import kotlinx.parcelize.Parcelize

interface PetDetailsNavigator {

    fun open(
        navController: NavController,
        petID: String,
    )

    @Parcelize
    data class PetDetailsArgs(
        val petID: String,
    ) : Parcelable
}