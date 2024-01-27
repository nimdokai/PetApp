package com.nimdokai.feature_petdetails.ui

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.core_util.navigation.PetDetailsNavigator.PetDetailsArgs
import com.nimdokai.core_util.navigation.getArgs
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core_domain.DomainResult
import com.nimdokai.pet.core_domain.model.PetDetails
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
    private val getPetDetails: GetPetDetailsUseCase,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(PetDetailsUiState())
    val state: Flow<PetDetailsUiState> = _state

    private val _event = MutableSharedFlow<PetDetailsEvent>()
    val event: Flow<PetDetailsEvent> = _event

    private val args: PetDetailsArgs = savedStateHandle.getArgs()

    fun onFirstLaunch() {
        // screen view analytics here
        getPetDetails()
    }

    fun onRetryGetPetDetails() {
        getPetDetails()
    }

    private fun getPetDetails() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        getPetDetails(args.petID)
            .collect { result ->
                when (result) {
                    is DomainResult.Success -> {
                        val petDetails = result.data.toUI()
                        _state.update { it.copy(isLoading = false, petDetailsUI = petDetails) }
                    }
                    DomainResult.NoInternet -> {
                        _event.emit(
                            PetDetailsEvent.ShowError(
                                title = R.string.dialog_no_internet_title,
                                message = R.string.dialog_no_internet_body,
                                buttonText = R.string.dialog_no_internet_retry,
                                ::onRetryGetPetDetails
                            )
                        )
                        _state.update { it.copy(isLoading = false) }
                    }
                    DomainResult.ServerError -> {
                        _event.emit(
                            PetDetailsEvent.ShowError(
                                title = R.string.dialog_server_error_title,
                                message = R.string.dialog_server_error_body,
                                buttonText = R.string.dialog_server_error_retry,
                                ::onRetryGetPetDetails
                            )
                        )
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
    }

}

private fun PetDetails.toUI(): PetDetailsUI {

    return PetDetailsUI(
        id = id,
        url = url,
//        breed = breed.run { BreedUI(id, name, description, wikiUrl) }
    )
}

data class PetDetailsUiState(
    val isLoading: Boolean = false,
    @StringRes val errorResId: Int? = null,
    val petDetailsUI: PetDetailsUI? = null,
)

sealed interface PetDetailsEvent {
    data class ShowError(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val buttonText: Int,
        val action: () -> Unit
    ) : PetDetailsEvent
}