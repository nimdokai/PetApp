package com.nimdokai.feature_petdetails.ui

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.feature_petdetails.navigation.PetDetailsNavigatorDefault.PetDetailsArgs
import com.nimdokai.pet.core.data.GetPetDetailsResponse
import com.nimdokai.pet.core.data.PetRepository
import com.nimdokai.pet.core.data.model.PetDetailsResponse
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
class PetDetailsViewModelTest {

    private val petRepository: PetRepository = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val dateFormatter: DateFormatter = mockk()

    private lateinit var viewModel: PetDetailsViewModel
    private lateinit var collector: ViewModelFlowCollector<PetDetailsUiState, PetDetailsEvent>

    @Before
    fun setUp() {
        every { savedStateHandle.get<PetDetailsArgs>("KEY_ARGS") } returns PetDetailsArgs(0)

        viewModel = PetDetailsViewModel(
            savedStateHandle,
            petRepository,
            TestCoroutineDispatchers,
            dateFormatter
        )

        collector = ViewModelFlowCollector(viewModel.state, viewModel.event)
    }

    @Test
    fun `GIVEN matchesRepository returns ServerError WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { petRepository.getPetDetails(0) } returns GetPetDetailsResponse.ServerError

            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = PetDetailsEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,

                action = viewModel::onRetryGetPetDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onFirstLaunch is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { petRepository.getPetDetails(0) } returns GetPetDetailsResponse.NoInternet

            //WHEN
            viewModel.onFirstLaunch()

            //THEN
            val expected = PetDetailsEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetPetDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onFirstLaunch is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetPetDetailsResponse.Success(
                PetDetailsResponse(
                    id = 0,
                    name = "name",
                    homeTeam = PetDetailsResponse.Team("TeamA", "urlA"),
                    awayTeam = PetDetailsResponse.Team("TeamB", "urlB"),
                    startTime = "2023-01-11T11:00:00.000000Z",
                    markets = listOf(
                        PetDetailsResponse.Market(
                            id = 1, "market1", listOf(
                                PetDetailsResponse.Contract(2, "contract1", "1.23")
                            )
                        ),
                    )
                )
            )

            coEvery { petRepository.getPetDetails(0) } returns result
            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onFirstLaunch()

            val expectedStates = listOf(
                PetDetailsUiState(isLoading = false, petDetailsUiModel = null),
                PetDetailsUiState(isLoading = true, petDetailsUiModel = null),
                PetDetailsUiState(isLoading = false, petDetailsUiModel = null),
                PetDetailsUiState(
                    isLoading = false,
                    petDetailsUiModel = PetDetailsUiModel(
                        id = 0,
                        name = "name",
                        homeTeam = PetDetailsUiModel.Team("TeamA", "urlA"),
                        awayTeam = PetDetailsUiModel.Team("TeamB", "urlB"),
                        startTime = "11:00",
                        detailsItems = listOf(
                            PetDetailsListItem.Market(id = 1, "market1"),
                            PetDetailsListItem.Contract(2, "contract1", "1.23"),
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
            coEvery { petRepository.getPetDetails(0) } returns GetPetDetailsResponse.ServerError

            //WHEN
            viewModel.onRetryGetPetDetails()

            //THEN
            val expected = PetDetailsEvent.ShowError(
                title = R.string.dialog_server_error_title,
                message = R.string.dialog_server_error_body,
                buttonText = R.string.dialog_server_error_retry,

                action = viewModel::onRetryGetPetDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }

    @Test
    fun `GIVEN matchesRepository returns NoInternet WHEN onRetryGetMatchDetails is called THEN ShowError event should be emitted`() =
        collector.runBlockingTest { _, events ->
            //GIVEN
            coEvery { petRepository.getPetDetails(0) } returns GetPetDetailsResponse.NoInternet

            //WHEN
            viewModel.onRetryGetPetDetails()

            //THEN
            val expected = PetDetailsEvent.ShowError(
                title = R.string.dialog_no_internet_title,
                message = R.string.dialog_no_internet_body,
                buttonText = R.string.dialog_no_internet_retry,
                action = viewModel::onRetryGetPetDetails
            )
            assertThat(events[0]).isEqualTo(expected)
        }


    @Test
    fun `GIVEN matchesRepository returns Success WHEN onRetryGetMatchDetails is called THEN state should be updated`() =
        collector.runBlockingTest { states, _ ->
            //GIVEN
            val result = GetPetDetailsResponse.Success(
                PetDetailsResponse(
                    id = 0,
                    name = "name",
                    homeTeam = PetDetailsResponse.Team("TeamA", "urlA"),
                    awayTeam = PetDetailsResponse.Team("TeamB", "urlB"),
                    startTime = "2023-01-11T11:00:00.000000Z",
                    markets = listOf(
                        PetDetailsResponse.Market(
                            id = 1, "market1", listOf(
                                PetDetailsResponse.Contract(2, "contract1", "1.23")
                            )
                        ),
                    )
                )
            )

            coEvery { petRepository.getPetDetails(0) } returns result
            every { dateFormatter.formatOnlyHourIfToday("2023-01-11T11:00:00.000000Z") } returns "11:00"

            //WHEN
            viewModel.onRetryGetPetDetails()

            val expectedStates = listOf(
                PetDetailsUiState(isLoading = false, petDetailsUiModel = null),
                PetDetailsUiState(isLoading = true, petDetailsUiModel = null),
                PetDetailsUiState(isLoading = false, petDetailsUiModel = null),
                PetDetailsUiState(
                    isLoading = false,
                    petDetailsUiModel = PetDetailsUiModel(
                        id = 0,
                        name = "name",
                        homeTeam = PetDetailsUiModel.Team("TeamA", "urlA"),
                        awayTeam = PetDetailsUiModel.Team("TeamB", "urlB"),
                        startTime = "11:00",
                        detailsItems = listOf(
                            PetDetailsListItem.Market(id = 1, "market1"),
                            PetDetailsListItem.Contract(2, "contract1", "1.23"),
                        )
                    )
                ),
            )

            //THEN
            assertThat(states).isEqualTo(expectedStates)
        }

}

