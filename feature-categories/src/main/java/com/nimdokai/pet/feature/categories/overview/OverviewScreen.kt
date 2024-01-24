package com.nimdokai.pet.feature.categories.overview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nimdokai.pet.feature.categories.list.OverviewViewModel

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel()
) {


    val currentConditionsUiState by viewModel.currentConditionsUiState.collectAsState()
    with(currentConditionsUiState.currentConditions) {
        Text(text = "Current temperature: $temperature")
    }

}