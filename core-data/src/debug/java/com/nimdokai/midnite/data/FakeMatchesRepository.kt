package com.nimdokai.midnite.data

import com.nimdokai.midnite.core.data.GetMatchDetailsResponse
import com.nimdokai.midnite.core.data.GetUpcomingMatchesResponse
import com.nimdokai.midnite.core.data.MatchesRepository
import com.nimdokai.midnite.core.data.model.Matches
import javax.inject.Inject

class FakeMatchesRepository @Inject constructor() : MatchesRepository {
    override suspend fun getUpcomingMatches(): GetUpcomingMatchesResponse {
        return GetUpcomingMatchesResponse.Success(
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
    }

    override suspend fun getMatchDetails(matchID: Int): GetMatchDetailsResponse {
        TODO("Not yet implemented")
    }

}
