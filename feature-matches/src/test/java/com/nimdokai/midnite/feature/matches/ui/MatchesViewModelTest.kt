package com.nimdokai.midnite.feature.matches.ui

import com.google.common.truth.Truth.assertThat
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.midnite.core.data.GetUpcomingMatchesResponse
import com.nimdokai.midnite.core.data.MatchesRepository
import com.nimdokai.midnite.core.data.model.Matches
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
class MatchesViewModelTest {

    private val matchesRepository: MatchesRepository = mockk()
    private val dateFormatter: DateFormatter = mockk()

    private lateinit var viewModel: MatchesViewModel
    private lateinit var collector: ViewModelFlowCollector<MatchesUiState, MatchesEvent>

    @Before
    fun setUp() {
        viewModel = MatchesViewModel(
            matchesRepository,
            TestCoroutineDispatchers,
            dateFormatter,
        )

        collector = ViewModelFlowCollector(viewModel.state, viewModel.event)
    }

    @Test
    fun `GIVEN matchesRepository returns ServerError WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { matchesRepository.getUpcomingMatches() } returns GetUpcomingMatchesResponse.ServerError

            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = MatchesEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                action = viewModel::onRetryGetUpcomingMatches
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { matchesRepository.getUpcomingMatches() } returns GetUpcomingMatchesResponse.NoInternet


            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = MatchesEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetUpcomingMatches
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onFirstLaunch is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetUpcomingMatchesResponse.Success(
                Matches(
                    listOf(
                        Matches.Match(
                            id = 0,
                            name = "name",
                            homeTeam = Matches.Team("TeamA", "urlA"),
                            awayTeam = Matches.Team("TeamB", "urlB"),
                            startTime = "2023-01-11T11:00:00.000000Z",
                        )
                    )
                )
            )
            coEvery { matchesRepository.getUpcomingMatches() } returns result

            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onFirstLaunch()

            val expectedStates = listOf(
                MatchesUiState(isLoading = false, matchItemList = emptyList()),
                MatchesUiState(isLoading = true, matchItemList = emptyList()),
                MatchesUiState(isLoading = false, matchItemList = emptyList()),
                MatchesUiState(
                    isLoading = false,
                    matchItemList = listOf(
                        MatchItemUI(
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
            coEvery { matchesRepository.getUpcomingMatches() } returns GetUpcomingMatchesResponse.ServerError

            //WHEN
            viewModel.onRetryGetUpcomingMatches()

            //THEN
            val expected = MatchesEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,
                action = viewModel::onRetryGetUpcomingMatches
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onRetryGetUpcomingMatches is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { matchesRepository.getUpcomingMatches() } returns GetUpcomingMatchesResponse.NoInternet

            //WHEN
            viewModel.onRetryGetUpcomingMatches()

            //THEN
            val expected = MatchesEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetUpcomingMatches
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onRetryGetUpcomingMatches is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetUpcomingMatchesResponse.Success(
                Matches(
                    listOf(
                        Matches.Match(
                            id = 0,
                            name = "name",
                            homeTeam = Matches.Team("TeamA", "urlA"),
                            awayTeam = Matches.Team("TeamB", "urlB"),
                            startTime = "2023-01-11T11:00:00.000000Z",
                        )
                    )
                )
            )
            coEvery { matchesRepository.getUpcomingMatches() } returns result

            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onRetryGetUpcomingMatches()

            val expectedStates = listOf(
                MatchesUiState(isLoading = false, matchItemList = emptyList()),
                MatchesUiState(isLoading = true, matchItemList = emptyList()),
                MatchesUiState(isLoading = false, matchItemList = emptyList()),
                MatchesUiState(
                    isLoading = false,
                    matchItemList = listOf(
                        MatchItemUI(
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
            val matchItem = MatchItemUI(
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

