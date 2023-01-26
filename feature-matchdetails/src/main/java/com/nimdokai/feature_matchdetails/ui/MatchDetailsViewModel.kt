package com.nimdokai.feature_matchdetails.ui

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.feature_matchdetails.navigation.MatchDetailsNavigatorDefault
import com.nimdokai.midnite.core.data.GetMatchDetailsResponse.*
import com.nimdokai.midnite.core.data.AnimalRepository
import com.nimdokai.midnite.core.resources.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val animalRepository: AnimalRepository,
    private val dispatchers: AppCoroutineDispatchers,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _state = MutableStateFlow(MatchDetailsUiState())
    val state: Flow<MatchDetailsUiState> = _state

    private val _event = MutableSharedFlow<MatchDetailsEvent>()
    val event: Flow<MatchDetailsEvent> = _event

    private val args: MatchDetailsNavigatorDefault.MatchDetailsArgs =
        MatchDetailsNavigatorDefault.getArgs(savedStateHandle)

    fun onFirstLaunch() {
        // screen view analytics here
        getMatchDetails()
    }

    fun onRetryGetMatchDetails() {
        getMatchDetails()
    }

    private fun getMatchDetails() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        val response = animalRepository.getAnimalDetails(args.matchId)
        _state.update { it.copy(isLoading = false) }
        when (response) {
            is Success -> onGetMatchDetailsResponseSuccess(response)
            NoInternet -> _event.emit(
                MatchDetailsEvent.ShowError(
                    title = R.string.dialog_no_internet_title,
                    message = R.string.dialog_no_internet_body,
                    buttonText = R.string.dialog_no_internet_retry,
                    action = ::onRetryGetMatchDetails
                )
            )
            ServerError -> _event.emit(
                MatchDetailsEvent.ShowError
                    (
                    title = R.string.dialog_server_error_title,
                    message = R.string.dialog_server_error_body,
                    buttonText = R.string.dialog_server_error_retry,
                    action = ::onRetryGetMatchDetails
                )
            )
        }
    }

    private fun onGetMatchDetailsResponseSuccess(response: Success) {
        _state.update { it.copy(matchDetailsUI = response.animalDetails.mapToUI(dateFormatter)) }
    }
}

data class MatchDetailsUiState(
    val isLoading: Boolean = false,
    @StringRes val errorResId: Int? = null,
    val matchDetailsUI: MatchDetailsUI? = null,
)

sealed interface MatchDetailsEvent {
    data class ShowError(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val buttonText: Int,
        val action: () -> Unit
    ) : MatchDetailsEvent
}