package com.nimdokai.core_util.navigation

import android.os.Parcelable
import androidx.navigation.NavController
import kotlinx.parcelize.Parcelize

interface PetCategoryFeedNavigator {

    fun open(
        navController: NavController,
        categoryID: String,
    )

    @Parcelize
    data class PetCategoryFeedArgs(val categoryID: String) : Parcelable
}