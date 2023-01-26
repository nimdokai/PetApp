package com.nimdokai.midnite.feature.matches.ui

import com.google.common.truth.Truth.assertThat
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.midnite.core.data.GetCategoriesResponse
import com.nimdokai.midnite.core.data.AnimalRepository
import com.nimdokai.midnite.core.data.model.AnimalCategories
import com.nimdokai.midnite.core.resources.R
import com.nimdokai.midnite.core.testing.TestCoroutineDispatchers
import com.nimdokai.midnite.core.testing.ViewModelFlowCollector
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimalCategoriesViewModelTest {

    private val animalRepository: AnimalRepository = mockk()
    private val dateFormatter: DateFormatter = mockk()

    private lateinit var viewModel: AnimalCategoriesViewModel
    private lateinit var collector: ViewModelFlowCollector<MatchesUiState, MatchesEvent>

    @Before
    fun setUp() {
        viewModel = AnimalCategoriesViewModel(
            animalRepository,
            TestCoroutineDispatchers,
            dateFormatter,
        )

        collector = ViewModelFlowCollector(viewModel.state, viewModel.event)
    }

    @Test
    fun `GIVEN matchesRepository returns ServerError WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { animalRepository.getCategories() } returns GetCategoriesResponse.ServerError

            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = MatchesEvent.ShowError(
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
            coEvery { animalRepository.getCategories() } returns GetCategoriesResponse.NoInternet


            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = MatchesEvent.ShowError(
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
                AnimalCategories(
                    listOf(
                        AnimalCategories.Match(
                            id = 0,
                            name = "name",
                            homeTeam = AnimalCategories.Team("TeamA", "urlA"),
                            awayTeam = AnimalCategories.Team("TeamB", "urlB"),
                            startTime = "2023-01-11T11:00:00.000000Z",
                        )
                    )
                )
            )
            coEvery { animalRepository.getCategories() } returns result

            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onFirstLaunch()

            val expectedStates = listOf(
                MatchesUiState(isLoading = false, categories = emptyList()),
                MatchesUiState(isLoading = true, categories = emptyList()),
                MatchesUiState(isLoading = false, categories = emptyList()),
                MatchesUiState(
                    isLoading = false,
                    categories = listOf(
                        AnimalCategoryItemUI(
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
            coEvery { animalRepository.getCategories() } returns GetCategoriesResponse.ServerError

            //WHEN
            viewModel.onRetryGetCategories()

            //THEN
            val expected = MatchesEvent.ShowError(
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
            coEvery { animalRepository.getCategories() } returns GetCategoriesResponse.NoInternet

            //WHEN
            viewModel.onRetryGetCategories()

            //THEN
            val expected = MatchesEvent.ShowError(
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
                AnimalCategories(
                    listOf(
                        AnimalCategories.Match(
                            id = 0,
                            name = "name",
                            homeTeam = AnimalCategories.Team("TeamA", "urlA"),
                            awayTeam = AnimalCategories.Team("TeamB", "urlB"),
                            startTime = "2023-01-11T11:00:00.000000Z",
                        )
                    )
                )
            )
            coEvery { animalRepository.getCategories() } returns result

            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onRetryGetCategories()

            val expectedStates = listOf(
                MatchesUiState(isLoading = false, categories = emptyList()),
                MatchesUiState(isLoading = true, categories = emptyList()),
                MatchesUiState(isLoading = false, categories = emptyList()),
                MatchesUiState(
                    isLoading = false,
                    categories = listOf(
                        AnimalCategoryItemUI(
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
            val matchItem = AnimalCategoryItemUI(
                id = 0,
                name = "name",
                homeTeam = TeamUI("TeamA", "urlA"),
                awayTeam = TeamUI("TeamB", "urlB"),
                startTime = "11:00",
            )

            //WHEN
            viewModel.onMatchClicked(
                matchItem
            )

            //THEN
            val expected = MatchesEvent.NavigateToMatchDetails(0)
            assertThat(events[0]).isEqualTo(expected)
        }

}

