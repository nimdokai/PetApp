package com.nimdokai.pet.feature.categories.ui

import com.google.common.truth.Truth.assertThat
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core.testing.TestCoroutineDispatchers
import com.nimdokai.pet.core.testing.ViewModelFlowCollector
import com.nimdokai.pet.core.testing.fakes.FakeGetPetCategoriesUseCase
import com.nimdokai.pet.core_domain.DomainResult
import com.nimdokai.pet.core_domain.model.PetCategory
import com.nimdokai.pet.feature.categories.list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PetCategoryViewModelTest {

    private lateinit var getPetCategoriesUseCase: FakeGetPetCategoriesUseCase

    private lateinit var viewModel: PetCategoriesViewModel
    private lateinit var collector: ViewModelFlowCollector<PetCategoriesUiState, PetCategoriesEvent>

    @Before
    fun setUp() {

        getPetCategoriesUseCase = FakeGetPetCategoriesUseCase()

        viewModel = PetCategoriesViewModel(
            getPetCategoriesUseCase,
            TestCoroutineDispatchers,
        )

        collector = ViewModelFlowCollector(viewModel.state, viewModel.event)
    }

    @Test
    fun `GIVEN getPetCategoriesUseCase returns ServerError WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runTest { _, events ->
            //GIVEN
            getPetCategoriesUseCase.domainResult = flowOf(DomainResult.ServerError)

            //WHEN
            viewModel.onFirstLaunch()
            delay(5000)

            //THEN
            val expected = PetCategoriesEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN getPetCategoriesUseCase returns NoInternet WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runTest { _, events ->
            //GIVEN
            getPetCategoriesUseCase.domainResult = flowOf(DomainResult.NoInternet)


            //WHEN
            viewModel.onFirstLaunch()
            delay(5000)

            //THEN
            val expected = PetCategoriesEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN getPetCategoriesUseCase returns Success WHEN onFirstLaunch is called THEN state should be updated`() =
        collector.runTest { states, _ ->
            //GIVEN
            val result = DomainResult.Success(
                listOf(
                    PetCategory(
                        id = 0,
                        name = "name",
                        imageUrl = "imageUrl"
                    )
                )
            )
            getPetCategoriesUseCase.domainResult = flowOf(result)

            //WHEN
            viewModel.onFirstLaunch()
            delay(5000)

            val expectedStates = listOf(
                PetCategoriesUiState(isLoading = false, currentConditions = emptyList()),
                PetCategoriesUiState(isLoading = true, currentConditions = emptyList()),
                PetCategoriesUiState(
                    isLoading = false,
                    currentConditions = listOf(
                        CurrentWeatherUi(
                            id = 0,
                            name = "name",
                            imageUrl = "imageUrl"
                        )
                    )
                ),
            )

            //THEN
            assertThat(states).isEqualTo(expectedStates)
        }

    @Test
    fun `GIVEN getPetCategoriesUseCase returns ServerError WHEN onRetryGetCategories is called THEN ShowError event should be emitted`() =
        collector.runTest { _, events ->
            //GIVEN
            getPetCategoriesUseCase.domainResult = flowOf(DomainResult.ServerError)

            //WHEN
            viewModel.onRetryGetCategories()
            delay(5000)

            //THEN
            val expected = PetCategoriesEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN getPetCategoriesUseCase returns NoInternet WHEN onRetryGetCategories is called THEN ShowError event should be emitted`() =
        collector.runTest { _, events ->
            //GIVEN
            getPetCategoriesUseCase.domainResult = flowOf(DomainResult.NoInternet)

            //WHEN
            viewModel.onRetryGetCategories()
            delay(5000)

            //THEN
            val expected = PetCategoriesEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN getPetCategoriesUseCase returns Success WHEN onRetryGetCategories is called THEN state should be updated`() =
        collector.runTest { states, _ ->
            //GIVEN
            val result = DomainResult.Success(
                listOf(
                    PetCategory(
                        id = 0,
                        name = "name",
                        imageUrl = "imageUrl"
                    )
                )
            )
            getPetCategoriesUseCase.domainResult = flowOf(result)

            //WHEN
            viewModel.onRetryGetCategories()
            delay(5000)

            val expectedStates = listOf(
                PetCategoriesUiState(isLoading = false, currentConditions = emptyList()),
                PetCategoriesUiState(isLoading = true, currentConditions = emptyList()),
                PetCategoriesUiState(
                    isLoading = false,
                    currentConditions = listOf(
                        CurrentWeatherUi(
                            id = 0,
                            name = "name",
                            imageUrl = "imageUrl"
                        )
                    )
                ),
            )

            //THEN
            assertThat(states).isEqualTo(expectedStates)
        }

}