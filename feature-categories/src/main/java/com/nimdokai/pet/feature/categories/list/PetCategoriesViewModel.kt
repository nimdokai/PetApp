package com.nimdokai.pet.feature.categories.list

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core_domain.DomainResult.*
import com.nimdokai.pet.core_domain.GetPetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCategoriesViewModel @Inject constructor(
    private val getCatCategoriesUseCase: GetPetCategoriesUseCase,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(PetCategoriesUiState())
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

    fun onCategoryClicked(category: PetCategoryItemUI) =
        viewModelScope.launch(dispatchers.computation) {
            _event.emit(PetCategoriesEvent.NavigateToCategoryFeed(category.id.toString()))
        }

    private fun getCategories() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        getCatCategoriesUseCase()
            .collect { result ->
                when (result) {
                    is Success -> {
                        val categories = result.data.map { it.toUI() }
                        _state.update { it.copy(isLoading = false, categories = categories) }
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

}

data class PetCategoriesUiState(
    val isLoading: Boolean = false,
    val categories: List<PetCategoryItemUI> = listOf(),
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

