package com.nimdokai.feature_petdetails.navigation

import androidx.navigation.NavController
import com.nimdokai.core_util.navigation.PetDetailsNavigator
import com.nimdokai.core_util.navigation.open
import com.nimdokai.pet.core.util.R
import javax.inject.Inject

class PetDetailsNavigatorDefault @Inject constructor() : PetDetailsNavigator {
    override fun open(
        navController: NavController,
        petID: String,
    ) {
        val args = PetDetailsNavigator.PetDetailsArgs(petID)
        navController.open(R.id.pet_details_nav_graph, args)
    }

}