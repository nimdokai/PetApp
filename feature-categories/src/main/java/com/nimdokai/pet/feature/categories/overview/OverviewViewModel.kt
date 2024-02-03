package com.nimdokai.pet.feature.categories.overview

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.core_util.date.DateFormatter
import com.nimdokai.core_util.permissions.PermissionCheckerImpl
import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.data.model.DailyForecast
import com.nimdokai.pet.core.data.model.HourlyForecast
import com.nimdokai.pet.core.data.model.Temperature
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core.resources.UnicodeDegreeSings
import com.nimdokai.pet.core.resources.getWeatherIcon
import com.nimdokai.pet.core_domain.DomainResult
import com.nimdokai.pet.core_domain.DomainResult.NoInternet
import com.nimdokai.pet.core_domain.DomainResult.ServerError
import com.nimdokai.pet.core_domain.DomainResult.Success
import com.nimdokai.pet.core_domain.GetCurrentConditionsUseCase
import com.nimdokai.pet.core_domain.GetDailyForecastUseCase
import com.nimdokai.pet.core_domain.GetHourlyForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val getCurrentConditionsUseCase: GetCurrentConditionsUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val dateFormatter: DateFormatter,
    private val permissionChecker: PermissionCheckerImpl,
) : ViewModel() {

    private val currentConditionsUiState = MutableStateFlow(CurrentConditionsUiState())
    private val hourlyForecastUiState = MutableStateFlow(HourlyForecastUiState())
    private val dailyForecastUiState = MutableStateFlow(DailyForecastUiState())
    private val event = MutableStateFlow<OverviewEvent>(OverviewEvent.Empty)

    val state = combine(
        currentConditionsUiState,
        hourlyForecastUiState,
        dailyForecastUiState,
        event
    ) { currentConditions, hourlyForecast, dailyForecast, event ->
        OverviewState(
            currentConditionsUiState = currentConditions,
            hourlyForecastUiState = hourlyForecast,
            dailyForecastUiState = dailyForecast,
            event = event
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        OverviewState()
    )

    init {
        getOverviewData()
    }

    private fun getOverviewData() {
        val hasPermission = permissionChecker.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasPermission) {
            requestOverviewData()
        } else {
            viewModelScope.launch {
                event.emit(OverviewEvent.RequestLocationPermission)
            }
        }
    }

    private fun requestOverviewData() {
        viewModelScope.launch {
            getCurrentConditionsUseCase()
                .map(::mapCurrentConditionsResult)
                .collect { conditions ->
                    currentConditionsUiState.value = conditions
                }
        }

        viewModelScope.launch {
            getHourlyForecastUseCase()
                .map(::mapHourlyForecastResult)
                .collect { hourlyForecast ->
                    hourlyForecastUiState.value = hourlyForecast
                }
        }

        viewModelScope.launch {
            getDailyForecastUseCase()
                .map(::mapDailyForecastResult)
                .collect { dailyForecast ->
                    dailyForecastUiState.value = dailyForecast
                }
        }
    }

    private suspend fun mapCurrentConditionsResult(result: DomainResult<out CurrentConditions>): CurrentConditionsUiState {
        return when (result) {
            is Success -> {
                val currentConditionsUi = result.data.toUi()
                currentConditionsUiState.value.copy(
                    isLoading = false,
                    currentConditions = currentConditionsUi
                )
            }

            NoInternet -> {
                onNoInternet(::requestOverviewData)
                currentConditionsUiState.value.copy(isLoading = false)
            }

            ServerError -> {
                onServerError(::requestOverviewData)
                currentConditionsUiState.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun mapHourlyForecastResult(result: DomainResult<out List<HourlyForecast>>): HourlyForecastUiState {
        return when (result) {
            is Success -> {
                val hourlyForecastUi = result.data.map { it.toUi() }
                hourlyForecastUiState.value.copy(
                    isLoading = false,
                    forecasts = hourlyForecastUi
                )
            }

            NoInternet -> {
                onNoInternet(::requestOverviewData)
                hourlyForecastUiState.value.copy(isLoading = false)
            }

            ServerError -> {
                onServerError(::requestOverviewData)
                hourlyForecastUiState.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun mapDailyForecastResult(result: DomainResult<out List<DailyForecast>>): DailyForecastUiState {
        return when (result) {
            is Success -> {
                val dailyForecastUi = result.data.map { it.toUi() }
                dailyForecastUiState.value.copy(
                    isLoading = false,
                    forecasts = dailyForecastUi
                )
            }

            NoInternet -> {
                onNoInternet(::requestOverviewData)
                dailyForecastUiState.value.copy(isLoading = false)
            }

            ServerError -> {
                onServerError(::requestOverviewData)
                dailyForecastUiState.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun onServerError(onRetry: () -> Unit) {
        event.emit(
            OverviewEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                onRetry
            )
        )
    }

    private suspend fun onNoInternet(onRetry: () -> Unit) {
        event.emit(
            OverviewEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                onRetry
            )
        )
    }

    private fun CurrentConditions.toUi(): CurrentWeatherUi {
        return CurrentWeatherUi(
            epochTime = epochTime,
            hasPrecipitation = hasPrecipitation,
            isDayTime = isDayTime,
            temperature = addMeasureUnitTo(temperatureSummary.current),
            pastMaxTemp = addMeasureUnitTo(temperatureSummary.pastMax),
            pastMinTemp = addMeasureUnitTo(temperatureSummary.pastMin),
            icon = getWeatherIcon(weatherType),
            description = description,
        )
    }

    private fun DailyForecast.toUi(): DailyForecastUi {
        return DailyForecastUi(
            temperature = addMeasureUnitTo(this.dayTemperature) + " / " + addMeasureUnitTo(this.nightTemperature),

            title = if (dateFormatter.isToday(epochDate)) "today" else dateFormatter.format(this.epochDate, "EEEE"),
            icon = getWeatherIcon(weatherType),
        )
    }

    private fun addMeasureUnitTo(temperature: Temperature): String {
        val unit = when (temperature) {
            is Temperature.Celsius -> UnicodeDegreeSings.Celsius
            is Temperature.Fahrenheit -> UnicodeDegreeSings.Fahrenheit
        }
        return "${temperature.value.toInt()}$unit"
    }

    private fun HourlyForecast.toUi(): HourlyForecastUi {
        return HourlyForecastUi(
            temperature = addMeasureUnitTo(temperature),
            time = dateFormatter.format(epochTime, "HH:mm"),
            icon = getWeatherIcon(weatherType),
        )
    }

    fun dismissDialog() {
        viewModelScope.launch {
            event.emit(OverviewEvent.Empty)
        }
    }

    fun onRequestLocationPermissionClicked() {
        viewModelScope.launch {
            dismissDialog()
            event.emit(OverviewEvent.RequestLocationPermission)
        }
    }

    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (isGranted && permission == Manifest.permission.ACCESS_COARSE_LOCATION) {
            getOverviewData()
        } else {
            viewModelScope.launch {
                event.emit(OverviewEvent.ShowPermissionDialog)
            }
        }
    }

    fun onGoToAppSettingsClicked() {
        viewModelScope.launch {
            event.emit(OverviewEvent.GoToAppSettings)
        }
    }

    fun onEventConsumed() {
        viewModelScope.launch {
            event.emit(OverviewEvent.Empty)
        }
    }
}
