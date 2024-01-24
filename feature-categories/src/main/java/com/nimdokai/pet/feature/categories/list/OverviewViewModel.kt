package com.nimdokai.pet.feature.categories.list

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.data.model.Temperature
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core.resources.UnicodeDegreeSings
import com.nimdokai.pet.core.resources.getWeatherIcon
import com.nimdokai.pet.core_domain.DomainResult
import com.nimdokai.pet.core_domain.DomainResult.NoInternet
import com.nimdokai.pet.core_domain.DomainResult.ServerError
import com.nimdokai.pet.core_domain.DomainResult.Success
import com.nimdokai.pet.core_domain.GetCurrentConditionsUseCase
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
                _event.emit(
                    PetCategoriesEvent.ShowError(
                        title = R.string.dialog_no_internet_title,
                        message = R.string.dialog_no_internet_body,
                        buttonText = R.string.dialog_no_internet_retry,
                        ::onRetryGetCategories
                    )
                )
                currentConditionsUiState.value.copy(isLoading = false)
            }

            ServerError -> {
                _event.emit(
                    PetCategoriesEvent.ShowError(
                        title = R.string.dialog_server_error_title,
                        message = R.string.dialog_server_error_body,
                        buttonText = R.string.dialog_server_error_retry,
                        ::onRetryGetCategories
                    )
                )
                currentConditionsUiState.value.copy(isLoading = false)
            }
        }
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

    private fun addMeasureUnitTo(temperature: Temperature): String {
        val unit = when (temperature) {
            is Temperature.Celsius -> UnicodeDegreeSings.Celsius
            is Temperature.Fahrenheit -> UnicodeDegreeSings.Fahrenheit
        }
        return "${temperature.value.toInt()}$unit"
    }

}


data class OverviewUiState(
    val currentConditions: CurrentConditionsUiState
)

data class CurrentConditionsUiState(
    val isLoading: Boolean = false,
    val currentConditions: CurrentWeatherUi,
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