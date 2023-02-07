package com.nimdokai.feature_petdetails.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.nimdokai.core_util.navigation.PetDetailsNavigator
import com.nimdokai.pet.core.util.R
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class PetDetailsNavigatorDefault @Inject constructor() : PetDetailsNavigator {

    companion object {
        private const val KEY_ARGS: String = "KEY_ARGS"

        internal fun getArgs(savedStateHandle: SavedStateHandle): PetDetailsArgs {
            return savedStateHandle.get<PetDetailsArgs>(KEY_ARGS)!!
        }
    }

    override fun open(
        navController: NavController,
        petID: Int,
    ) {
        val bundle = Bundle().apply {
            putParcelable(KEY_ARGS, PetDetailsArgs(petID))
        }
        navController.navigate(R.id.pet_details_nav_graph, bundle, defaultNavOptions)
    }

    @Parcelize
    data class PetDetailsArgs(
        val petID: Int
    ) : Parcelable

}