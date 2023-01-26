package com.nimdokai.midnite.feature.matches.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.midnite.core.data.AnimalRepository
import com.nimdokai.midnite.core.data.GetCategoriesResponse
import com.nimdokai.midnite.core.resources.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalCategoriesViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(MatchesUiState())
    val state: Flow<MatchesUiState> = _state

    private val _event = MutableSharedFlow<MatchesEvent>()
    val event: Flow<MatchesEvent> = _event

    fun onFirstLaunch() {
        // screen view analytics here
        getCategories()
    }

    fun onRetryGetCategories() {
        getCategories()
    }

    fun onMatchClicked(matchItem: AnimalCategoryItemUI) =
        viewModelScope.launch(dispatchers.computation) {
            _event.emit(MatchesEvent.NavigateToMatchDetails(matchItem.id))
        }

    private fun getCategories() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        val response = animalRepository.getCategories()
        _state.update { it.copy(isLoading = false) }
        when (response) {
            is GetCategoriesResponse.Success -> onResponseSuccess(response)
            GetCategoriesResponse.NoInternet -> _event.emit(
                MatchesEvent.ShowError(
                    title = R.string.dialog_no_internet_title,
                    message = R.string.dialog_no_internet_body,
                    buttonText = R.string.dialog_no_internet_retry,
                    ::onRetryGetCategories
                )
            )
            GetCategoriesResponse.ServerError -> _event.emit(
                MatchesEvent.ShowError(
                    title = R.string.dialog_server_error_title,
                    message = R.string.dialog_server_error_body,
                    buttonText = R.string.dialog_server_error_retry,
                    ::onRetryGetCategories
                )
            )

        }
    }

    private fun onResponseSuccess(response: GetCategoriesResponse.Success) {
        val categories = response.categories.map { it.toUI() }
        _state.update { it.copy(categories = categories) }
    }
}

data class MatchesUiState(
    val isLoading: Boolean = false,
    val categories: List<AnimalCategoryItemUI> = listOf(),
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

