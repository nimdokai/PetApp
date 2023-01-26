package com.nimdokai.pet.feature.categories.ui

import com.google.common.truth.Truth.assertThat
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.pet.core.data.GetCategoriesResponse
import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core.data.model.PetCategories
import com.nimdokai.pet.core.resources.R
import com.nimdokai.pet.core.testing.TestCoroutineDispatchers
import com.nimdokai.pet.core.testing.ViewModelFlowCollector
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PetCategoriesViewModelTest {

    private val petRepository: PetRepository = mockk()
    private val dateFormatter: DateFormatter = mockk()

    private lateinit var viewModel: PetCategoriesViewModel
    private lateinit var collector: ViewModelFlowCollector<CategoriesUiState, CategoriesEvent>

    @Before
    fun setUp() {
        viewModel = PetCategoriesViewModel(
            petRepository,
            TestCoroutineDispatchers,
            dateFormatter,
        )

        collector = ViewModelFlowCollector(viewModel.state, viewModel.event)
    }

    @Test
    fun `GIVEN matchesRepository returns ServerError WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { petRepository.getCategories() } returns GetCategoriesResponse.ServerError

            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = CategoriesEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { petRepository.getCategories() } returns GetCategoriesResponse.NoInternet


            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = CategoriesEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onFirstLaunch is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetCategoriesResponse.Success(
                PetCategories(
                    listOf(
                        PetCategories.Match(
                            id = 0,
                            name = "name",
                            homeTeam = PetCategories.Team("TeamA", "urlA"),
                            awayTeam = PetCategories.Team("TeamB", "urlB"),
                            startTime = "2023-01-11T11:00:00.000000Z",
                        )
                    )
                )
            )
            coEvery { petRepository.getCategories() } returns result

            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onFirstLaunch()

            val expectedStates = listOf(
                CategoriesUiState(isLoading = false, categories = emptyList()),
                CategoriesUiState(isLoading = true, categories = emptyList()),
                CategoriesUiState(isLoading = false, categories = emptyList()),
                CategoriesUiState(
                    isLoading = false,
                    categories = listOf(
                        PetCategoryItemUI(
                            id = 0,
                            name = "name",
                            homeTeam = TeamUI("TeamA", "urlA"),
                            awayTeam = TeamUI("TeamB", "urlB"),
                            startTime = "11:00",
                        )
                    )
                ),
            )

            //THEN
            assertThat(states).isEqualTo(expectedStates)
        }

    @Test
    fun `GIVEN matchesRepository returns ServerError WHEN onRetryGetUpcomingMatches is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { petRepository.getCategories() } returns GetCategoriesResponse.ServerError

            //WHEN
            viewModel.onRetryGetCategories()

            //THEN
            val expected = CategoriesEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onRetryGetUpcomingMatches is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { petRepository.getCategories() } returns GetCategoriesResponse.NoInternet

            //WHEN
            viewModel.onRetryGetCategories()

            //THEN
            val expected = CategoriesEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetCategories
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onRetryGetUpcomingMatches is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetCategoriesResponse.Success(
                PetCategories(
                    listOf(
                        PetCategories.Match(
                            id = 0,
                            name = "name",
                            homeTeam = PetCategories.Team("TeamA", "urlA"),
                            awayTeam = PetCategories.Team("TeamB", "urlB"),
                            startTime = "2023-01-11T11:00:00.000000Z",
                        )
                    )
                )
            )
            coEvery { petRepository.getCategories() } returns result

            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onRetryGetCategories()

            val expectedStates = listOf(
                CategoriesUiState(isLoading = false, categories = emptyList()),
                CategoriesUiState(isLoading = true, categories = emptyList()),
                CategoriesUiState(isLoading = false, categories = emptyList()),
                CategoriesUiState(
                    isLoading = false,
                    categories = listOf(
                        PetCategoryItemUI(
                            id = 0,
                            name = "name",
                            homeTeam = TeamUI("TeamA", "urlA"),
                            awayTeam = TeamUI("TeamB", "urlB"),
                            startTime = "11:00",
                        )
                    )
                ),
            )

            //THEN
            assertThat(states).isEqualTo(expectedStates)
        }

    @Test
    fun `WHEN onMatchClicked is called THEN NavigateToMatchDetails event should be emitted`() =
        collector.runBlockingTest { _, events ->

            //GIVEN
            val matchItem = PetCategoryItemUI(
                id = 0,
                name = "name",
                homeTeam = TeamUI("TeamA", "urlA"),
                awayTeam = TeamUI("TeamB", "urlB"),
                startTime = "11:00",
            )

            //WHEN
            viewModel.onPetClicked(
                matchItem
            )

            //THEN
            val expected = CategoriesEvent.NavigateToPetDetails(0)
            assertThat(events[0]).isEqualTo(expected)
        }

}

