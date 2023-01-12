package com.nimdokai.midnite.feature.matches.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.midnite.core.data.GetUpcomingMatchesResponse
import com.nimdokai.midnite.core.data.MatchesRepository
import com.nimdokai.midnite.core.resources.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val dispatchers: AppCoroutineDispatchers,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _state = MutableStateFlow(MatchesUiState())
    val state: Flow<MatchesUiState> = _state

    private val _event = MutableSharedFlow<MatchesEvent>()
    val event: Flow<MatchesEvent> = _event

    fun onFirstLaunch() {
        // screen view analytics here
        getUpcomingMatches()
    }

    fun onRetryGetUpcomingMatches() {
        getUpcomingMatches()
    }

    fun onMatchClicked(matchItem: MatchItemUI) = viewModelScope.launch(dispatchers.computation) {
        _event.emit(MatchesEvent.NavigateToMatchDetails(matchItem.id))
    }

    private fun getUpcomingMatches() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        val response = matchesRepository.getUpcomingMatches()
        _state.update { it.copy(isLoading = false) }
        when (response) {
            is GetUpcomingMatchesResponse.Success -> onUpcomingMatchesResponseSuccess(response)
            GetUpcomingMatchesResponse.NoInternet -> _event.emit(
                MatchesEvent.ShowError(
                    title = R.string.dialog_no_internet_title,
                    message = R.string.dialog_no_internet_body,
                    buttonText = R.string.dialog_no_internet_retry,
                    ::onRetryGetUpcomingMatches
                )
            )
            GetUpcomingMatchesResponse.ServerError -> _event.emit(
                MatchesEvent.ShowError(
                    title = R.string.dialog_server_error_title,
                    message = R.string.dialog_server_error_body,
                    buttonText = R.string.dialog_server_error_retry,
                    ::onRetryGetUpcomingMatches
                )
            )

        }
    }

    private fun onUpcomingMatchesResponseSuccess(response: GetUpcomingMatchesResponse.Success) {
        val matchesUI = response.matches.data.map {
            MatchItemUI(
                id = it.id,
                name = it.name,
                homeTeam = it.homeTeam.toUI(),
                awayTeam = it.awayTeam.toUI(),
                startTime = dateFormatter.formatOnlyHourIfToday(it.startTime)
            )
        }
        _state.update { it.copy(matchItemList = matchesUI) }
    }

}

data class MatchesUiState(
    val isLoading: Boolean = false,
    val matchItemList: List<MatchItemUI> = listOf(),
)

sealed interface MatchesEvent {
    data class NavigateToMatchDetails(val matchId: Int) : MatchesEvent
    data class ShowError(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val buttonText: Int,
        val action: () -> Unit
    ) : MatchesEvent
}

