package com.nimdokai.pet.feature.categories.list

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.pet.core.data.model.CurrentConditions
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core_domain.DomainResult.*
import com.nimdokai.pet.core_domain.GetCurrentConditionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCategoriesViewModel @Inject constructor(
    private val getCurrentConditionsUseCase: GetCurrentConditionsUseCase,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(
        PetCategoriesUiState(
            currentConditions = CurrentWeatherUi(
                epochTime = 0,
                hasPrecipitation = false,
                isDayTime = false,
                temperature = ""
            )
        )
    )
    val state: Flow<PetCategoriesUiState> = _state

    private val _event = MutableSharedFlow<PetCategoriesEvent>()
    val event: Flow<PetCategoriesEvent> = _event

    fun onFirstLaunch() {
        // screen view analytics here
        getCategories()
    }

    fun onRetryGetCategories() {
        getCategories()
    }

    fun onCategoryClicked(category: CurrentWeatherUi) =
        viewModelScope.launch(dispatchers.computation) {
            // TODO
        }

    private fun getCategories() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        getCurrentConditionsUseCase()
            .collect { result ->
                when (result) {
                    is Success -> {
                        val currentConditionsUi = result.data.toUi()
                        _state.update { it.copy(isLoading = false, currentConditions = currentConditionsUi) }
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
                        _state.update { it.copy(isLoading = false) }
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
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
    }

    private fun CurrentConditions.toUi(): CurrentWeatherUi = CurrentWeatherUi(
        epochTime,
        hasPrecipitation,
        isDayTime,
        temperature.value.toString(),
    )

}


data class PetCategoriesUiState(
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