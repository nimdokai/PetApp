package com.nimdokai.core_util.navigation

import android.os.Parcelable
import androidx.navigation.NavController

interface MatchDetailsNavigator {

    public fun open(
        navController: NavController,
        matchId: Int,
    )

}