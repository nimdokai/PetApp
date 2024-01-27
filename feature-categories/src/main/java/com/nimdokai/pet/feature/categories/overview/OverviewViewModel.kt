package com.nimdokai.pet.feature.categories.overview

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.core_util.date.DateFormatter
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
import com.nimdokai.pet.feature.categories.list.CurrentWeatherUi
import com.nimdokai.pet.feature.categories.list.DailyForecastUi
import com.nimdokai.pet.feature.categories.list.HourlyForecastUi
import com.nimdokai.pet.feature.categories.list.emptyCurrentWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    getCurrentConditionsUseCase: GetCurrentConditionsUseCase,
    getHourlyForecastUseCase: GetHourlyForecastUseCase,
    getDailyForecastUseCase: GetDailyForecastUseCase,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _event = MutableSharedFlow<PetCategoriesEvent>()
    val event: Flow<PetCategoriesEvent> = _event

    val currentConditionsUiState: StateFlow<CurrentConditionsUiState> =
        getCurrentConditionsUseCase()
            .map(::mapCurrentConditionsResult)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CurrentConditionsUiState(
                    currentConditions = emptyCurrentWeatherUiState,
                    isLoading = true
                )
            )

    val hourlyForecastUiState: StateFlow<HourlyForecastUiState> =
        getHourlyForecastUseCase()
            .map(::mapHourlyForecastResult)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HourlyForecastUiState(
                    title = R.string.hourly_forecast,
                    forecasts = emptyList(),
                    isLoading = true
                )
            )

    val dailyForecastUiState: StateFlow<DailyForecastUiState> =
        getDailyForecastUseCase()
            .map(::mapDailyForecastResult)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DailyForecastUiState(
                    title = R.string.daily_forecast,
                    forecasts = emptyList(),
                    isLoading = true
                )
            )

    fun onFirstLaunch() {
        // screen view analytics here
    }

    fun onRetryGetCategories() {
    }

    fun onCategoryClicked() =
        viewModelScope.launch(dispatchers.computation) {
            // TODO
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
                onNoInternet(::onRetryGetCategories)
                currentConditionsUiState.value.copy(isLoading = false)
            }

            ServerError -> {
                onServerError(::onRetryGetCategories)
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
                onNoInternet(::onRetryGetCategories)
                hourlyForecastUiState.value.copy(isLoading = false)
            }

            ServerError -> {
                onServerError(::onRetryGetCategories)
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
                onNoInternet(::onRetryGetCategories)
                dailyForecastUiState.value.copy(isLoading = false)
            }

            ServerError -> {
                onServerError(::onRetryGetCategories)
                dailyForecastUiState.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun onServerError(onRetry: () -> Unit) {
        _event.emit(
            PetCategoriesEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                onRetry
            )
        )
    }

    private suspend fun onNoInternet(onRetry: () -> Unit) {
        _event.emit(
            PetCategoriesEvent.ShowError(
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
            temperature = addMeasureUnitTo(this.dayTemperature) + "/" + addMeasureUnitTo(this.nightTemperature),

            title = if (dateFormatter.isToday(epochDate)) "today" else dateFormatter.format(this.epochDate, "dddd"),
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
}

data class OverviewUiState(
    val currentConditions: CurrentConditionsUiState
)

data class CurrentConditionsUiState(
    val isLoading: Boolean = false,
    val currentConditions: CurrentWeatherUi,
)

data class HourlyForecastUiState(
    @StringRes val title: Int,
    val isLoading: Boolean = false,
    val forecasts: List<HourlyForecastUi>,
)

data class DailyForecastUiState(
    @StringRes val title: Int,
    val isLoading: Boolean = false,
    val forecasts: List<DailyForecastUi>,
)

sealed interface PetCategoriesEvent {
    data class NavigateToCategoryFeed(val categoryID: String) : PetCategoriesEvent
    data class ShowError(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val buttonText: Int,
        val action: () -> Unit
    ) : PetCategoriesEvent
}