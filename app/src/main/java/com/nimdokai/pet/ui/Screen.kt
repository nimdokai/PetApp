package com.nimdokai.pet.ui

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
) {
    data object Home : Screen("home")
}