package com.nimdokai.pet.feature.categories.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core_domain.DomainResult.*
import com.nimdokai.pet.core_domain.GetCatCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCategoriesViewModel @Inject constructor(
    private val getCatCategoriesUseCase: GetCatCategoriesUseCase,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesUiState())
    val state: Flow<CategoriesUiState> = _state

    private val _event = MutableSharedFlow<CategoriesEvent>()
    val event: Flow<CategoriesEvent> = _event

    fun onFirstLaunch() {
        // screen view analytics here
        getCategories()
    }

    fun onRetryGetCategories() {
        getCategories()
    }

    fun onPetClicked(matchItem: PetCategoryItemUI) =
        viewModelScope.launch(dispatchers.computation) {
            _event.emit(CategoriesEvent.NavigateToPetDetails(matchItem.id))
        }

    private fun getCategories() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        val result = getCatCategoriesUseCase()
        _state.update { it.copy(isLoading = false) }
        when (result) {
            is Success -> {
                val categories = result.data.map { it.toUI() }
                _state.update { it.copy(categories = categories) }
            }
            NoInternet -> _event.emit(
                CategoriesEvent.ShowError(
                    title = R.string.dialog_no_internet_title,
                    message = R.string.dialog_no_internet_body,
                    buttonText = R.string.dialog_no_internet_retry,
                    ::onRetryGetCategories
                )
            )
            ServerError -> _event.emit(
                CategoriesEvent.ShowError(
                    title = R.string.dialog_server_error_title,
                    message = R.string.dialog_server_error_body,
                    buttonText = R.string.dialog_server_error_retry,
                    ::onRetryGetCategories
                )
            )

        }
    }

}

data class CategoriesUiState(
    val isLoading: Boolean = false,
    val categories: List<PetCategoryItemUI> = listOf(),
)

sealed interface CategoriesEvent {
    data class NavigateToPetDetails(val petID: Int) : CategoriesEvent
    data class ShowError(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val buttonText: Int,
        val action: () -> Unit
    ) : CategoriesEvent
}

