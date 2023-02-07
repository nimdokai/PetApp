package com.nimdokai.pet.feature.categories.feed

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimdokai.core_util.AppCoroutineDispatchers
import com.nimdokai.core_util.navigation.PetCategoryFeedNavigator.PetCategoryFeedArgs
import com.nimdokai.core_util.navigation.getArgs
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core_domain.DomainResult.*
import com.nimdokai.pet.core_domain.GetPetCategoryFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCategoryViewModel @Inject constructor(
    private val getCatCategoriesUseCase: GetPetCategoryFeedUseCase,
    private val dispatchers: AppCoroutineDispatchers,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: Flow<State> = _state

    private val _event = MutableSharedFlow<Event>()
    val event: Flow<Event> = _event

    private val args: PetCategoryFeedArgs = savedStateHandle.getArgs()

    fun onFirstLaunch() {
        // screen view analytics here
        getFeed()
    }

    fun onRetryGetCategories() {
        getFeed()
    }

    private fun getFeed() = viewModelScope.launch(dispatchers.io) {
        _state.update { it.copy(isLoading = true) }
        getCatCategoriesUseCase(args.categoryID)
            .collect { result ->
                when (result) {
                    is Success -> {
                        val categories = result.data.map { it.toUI() }
                        _state.update { it.copy(isLoading = false, categories = categories) }
                    }
                    NoInternet -> {
                        _event.emit(
                            Event.ShowError(
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
                            Event.ShowError(
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

    fun onPetClicked(item: Any) {


    }

    data class State(
        val isLoading: Boolean = false,
        val categories: List<PetCategoryFeedItemUI> = listOf(),
    )

    sealed interface Event {
        data class NavigateToPetDetails(val petID: Int) : Event
        data class ShowError(
            @StringRes val title: Int,
            @StringRes val message: Int,
            @StringRes val buttonText: Int,
            val action: () -> Unit
        ) : Event
    }


}


