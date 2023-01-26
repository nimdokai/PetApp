package com.nimdokai.feature_petdetails.ui

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.feature_petdetails.navigation.PetDetailsNavigatorDefault
import com.nimdokai.pet.core.data.GetPetDetailsResponse.*
import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core.resources.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val petRepository: PetRepository,
    private val dispatchers: AppCoroutineDispatchers,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _state = MutableStateFlow(PetDetailsUiState())
    val state: Flow<PetDetailsUiState> = _state

    private val _event = MutableSharedFlow<PetDetailsEvent>()
    val event: Flow<PetDetailsEvent> = _event

    private val args: PetDetailsNavigatorDefault.PetDetailsArgs =
        PetDetailsNavigatorDefault.getArgs(savedStateHandle)

    fun onFirstLaunch() {
        // screen view analytics here
        getPetDetails()
    }

    fun onRetryGetPetDetails() {
        getPetDetails()
    }

    private fun getPetDetails() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        val response = petRepository.getPetDetails(args.petID)
        _state.update { it.copy(isLoading = false) }
        when (response) {
            is Success -> onGetPetDetailsResponseSuccess(response)
            NoInternet -> _event.emit(
                PetDetailsEvent.ShowError(
                    title = R.string.dialog_no_internet_title,
                    message = R.string.dialog_no_internet_body,
                    buttonText = R.string.dialog_no_internet_retry,
                    action = ::onRetryGetPetDetails
                )
            )
            ServerError -> _event.emit(
                PetDetailsEvent.ShowError
                    (
                    title = R.string.dialog_server_error_title,
                    message = R.string.dialog_server_error_body,
                    buttonText = R.string.dialog_server_error_retry,
                    action = ::onRetryGetPetDetails
                )
            )
        }
    }

    private fun onGetPetDetailsResponseSuccess(response: Success) {
        _state.update { it.copy(petDetailsUiModel = TODO()) }
    }
}

data class PetDetailsUiState(
    val isLoading: Boolean = false,
    @StringRes val errorResId: Int? = null,
    val petDetailsUiModel: PetDetailsUiModel? = null,
)

sealed interface PetDetailsEvent {
    data class ShowError(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val buttonText: Int,
        val action: () -> Unit
    ) : PetDetailsEvent
}