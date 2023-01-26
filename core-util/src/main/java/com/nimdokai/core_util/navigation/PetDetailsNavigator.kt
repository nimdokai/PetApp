package com.nimdokai.core_util.navigation

import androidx.navigation.NavController

interface PetDetailsNavigator {

    public fun open(
        navController: NavController,
        petID: Int,
    )

}