package com.nimdokai.pet.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nimdokai.pet.feature.categories.overview.OverviewScreen


@Composable
fun WeatherAppContent() {
    val navController = rememberNavController()

    WeatherNavHost(navController = navController)
}

@Composable
fun WeatherNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            OverviewScreen()
        }
    }
}