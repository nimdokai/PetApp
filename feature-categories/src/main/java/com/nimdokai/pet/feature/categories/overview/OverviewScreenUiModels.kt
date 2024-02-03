package com.nimdokai.pet.feature.categories.overview

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nimdokai.pet.core.resources.R

data class OverviewState(
    val currentConditionsUiState: CurrentConditionsUiState = CurrentConditionsUiState(),
    val hourlyForecastUiState: HourlyForecastUiState = HourlyForecastUiState(),
    val dailyForecastUiState: DailyForecastUiState = DailyForecastUiState(),
    val event: OverviewEvent = OverviewEvent.Empty,
)

data class CurrentConditionsUiState(
    val isLoading: Boolean = false,
    val currentConditions: CurrentWeatherUi = emptyCurrentWeatherUiState,
)

data class HourlyForecastUiState(
    @StringRes val title: Int = R.string.hourly_forecast,
    val isLoading: Boolean = false,
    val forecasts: List<HourlyForecastUi> = emptyList()
)

data class DailyForecastUiState(
    @StringRes val title: Int = R.string.daily_forecast,
    val isLoading: Boolean = false,
    val forecasts: List<DailyForecastUi> = emptyList(),
)

sealed interface OverviewEvent {
    data object RequestLocationPermission : OverviewEvent
    data object GoToAppSettings : OverviewEvent
    data object NavigateToDayDetails : OverviewEvent
    data object Empty: OverviewEvent
    data class ShowError(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val buttonText: Int,
        val action: () -> Unit
    ) : OverviewEvent
    data object ShowPermissionDialog : OverviewEvent
}

data class CurrentWeatherUi(
    val epochTime: Int,
    val hasPrecipitation: Boolean,
    val isDayTime: Boolean,
    val temperature: String,
    val pastMaxTemp: String,
    val pastMinTemp: String,
    @DrawableRes val icon: Int,
    val description: String,
)

val emptyCurrentWeatherUiState = CurrentWeatherUi(
    epochTime = 0,
    hasPrecipitation = false,
    isDayTime = false,
    temperature = "",
    pastMaxTemp = "",
    pastMinTemp = "",
    icon = 0,
    description = "",
)

data class HourlyForecastUi(
    val temperature: String,
    val time: String,
    @DrawableRes val icon: Int,
)

data class DailyForecastUi(
    val title: String,
    val temperature: String,
    @DrawableRes val icon: Int,
)
