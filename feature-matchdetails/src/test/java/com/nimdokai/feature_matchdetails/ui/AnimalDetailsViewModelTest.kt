package com.nimdokai.feature_matchdetails.ui

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.feature_matchdetails.navigation.MatchDetailsNavigatorDefault.MatchDetailsArgs
import com.nimdokai.feature_matchdetails.ui.MatchDetailsUI.MatchDetailsListItem
import com.nimdokai.midnite.core.data.GetMatchDetailsResponse
import com.nimdokai.midnite.core.data.AnimalRepository
import com.nimdokai.midnite.core.data.model.AnimalDetails
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
class AnimalDetailsViewModelTest {

    private val animalRepository: AnimalRepository = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val dateFormatter: DateFormatter = mockk()

    private lateinit var viewModel: MatchDetailsViewModel
    private lateinit var collector: ViewModelFlowCollector<MatchDetailsUiState, MatchDetailsEvent>

    @Before
    fun setUp() {
        every { savedStateHandle.get<MatchDetailsArgs>("KEY_ARGS") } returns MatchDetailsArgs(0)

        viewModel = MatchDetailsViewModel(
            savedStateHandle,
            animalRepository,
            TestCoroutineDispatchers,
            dateFormatter
        )

        collector = ViewModelFlowCollector(viewModel.state, viewModel.event)
    }

    @Test
    fun `GIVEN matchesRepository returns ServerError WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { animalRepository.getAnimalDetails(0) } returns GetMatchDetailsResponse.ServerError

            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = MatchDetailsEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,

                action = viewModel::onRetryGetMatchDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { animalRepository.getAnimalDetails(0) } returns GetMatchDetailsResponse.NoInternet

            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = MatchDetailsEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetMatchDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onFirstLaunch is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetMatchDetailsResponse.Success(
                AnimalDetails(
                    id = 0,
                    name = "name",
                    homeTeam = AnimalDetails.Team("TeamA", "urlA"),
                    awayTeam = AnimalDetails.Team("TeamB", "urlB"),
                    startTime = "2023-01-11T11:00:00.000000Z",
                    markets = listOf(
                        AnimalDetails.Market(
                            id = 1, "market1", listOf(
                                AnimalDetails.Contract(2, "contract1", "1.23")
                            )
                        ),
                    )
                )
            )

            coEvery { animalRepository.getAnimalDetails(0) } returns result
            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onFirstLaunch()

            val expectedStates = listOf(
                MatchDetailsUiState(isLoading = false, matchDetailsUI = null),
                MatchDetailsUiState(isLoading = true, matchDetailsUI = null),
                MatchDetailsUiState(isLoading = false, matchDetailsUI = null),
                MatchDetailsUiState(
                    isLoading = false,
                    matchDetailsUI = MatchDetailsUI(
                        id = 0,
                        name = "name",
                        homeTeam = MatchDetailsUI.Team("TeamA", "urlA"),
                        awayTeam = MatchDetailsUI.Team("TeamB", "urlB"),
                        startTime = "11:00",
                        detailsItems = listOf(
                            MatchDetailsListItem.Market(id = 1, "market1"),
                            MatchDetailsListItem.Contract(2, "contract1", "1.23"),
                        )
                    )
                ),
            )

            //THEN
            assertThat(states).isEqualTo(expectedStates)
        }

    @Test
    fun `GIVEN matchesRepository returns ServerError WHEN onRetryGetMatchDetails is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { animalRepository.getAnimalDetails(0) } returns GetMatchDetailsResponse.ServerError

            //WHEN
            viewModel.onRetryGetMatchDetails()

            //THEN
            val expected = MatchDetailsEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,

                action = viewModel::onRetryGetMatchDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onRetryGetMatchDetails is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { animalRepository.getAnimalDetails(0) } returns GetMatchDetailsResponse.NoInternet

            //WHEN
            viewModel.onRetryGetMatchDetails()

            //THEN
            val expected = MatchDetailsEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetMatchDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onRetryGetMatchDetails is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetMatchDetailsResponse.Success(
                AnimalDetails(
                    id = 0,
                    name = "name",
                    homeTeam = AnimalDetails.Team("TeamA", "urlA"),
                    awayTeam = AnimalDetails.Team("TeamB", "urlB"),
                    startTime = "2023-01-11T11:00:00.000000Z",
                    markets = listOf(
                        AnimalDetails.Market(
                            id = 1, "market1", listOf(
                                AnimalDetails.Contract(2, "contract1", "1.23")
                            )
                        ),
                    )
                )
            )

            coEvery { animalRepository.getAnimalDetails(0) } returns result
            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onRetryGetMatchDetails()

            val expectedStates = listOf(
                MatchDetailsUiState(isLoading = false, matchDetailsUI = null),
                MatchDetailsUiState(isLoading = true, matchDetailsUI = null),
                MatchDetailsUiState(isLoading = false, matchDetailsUI = null),
                MatchDetailsUiState(
                    isLoading = false,
                    matchDetailsUI = MatchDetailsUI(
                        id = 0,
                        name = "name",
                        homeTeam = MatchDetailsUI.Team("TeamA", "urlA"),
                        awayTeam = MatchDetailsUI.Team("TeamB", "urlB"),
                        startTime = "11:00",
                        detailsItems = listOf(
                            MatchDetailsListItem.Market(id = 1, "market1"),
                            MatchDetailsListItem.Contract(2, "contract1", "1.23"),
                        )
                    )
                ),
            )

            //THEN
            assertThat(states).isEqualTo(expectedStates)
        }

}

