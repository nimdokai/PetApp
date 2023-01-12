package com.nimdokai.feature_matchdetails.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.nimdokai.core_util.navigation.MatchDetailsNavigator
import com.nimdokai.midnite.core.util.R
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class MatchDetailsNavigatorDefault @Inject constructor() : MatchDetailsNavigator {

    companion object {
        private const val KEY_ARGS: String = "KEY_ARGS"

        internal fun getArgs(savedStateHandle: SavedStateHandle): MatchDetailsArgs {
            return savedStateHandle.get<MatchDetailsArgs>(KEY_ARGS)!!
        }
    }

    override fun open(
        navController: NavController,
        matchId: Int,
    ) {
        val bundle = Bundle().apply {
            putParcelable(KEY_ARGS, MatchDetailsArgs(matchId))
        }
        navController.navigate(R.id.match_details_nav_graph, bundle, defaultNavOptions)
    }

    @Parcelize
    data class MatchDetailsArgs(
        val matchId: Int
    ) : Parcelable

}