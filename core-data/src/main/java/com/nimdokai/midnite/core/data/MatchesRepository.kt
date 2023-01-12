package com.nimdokai.midnite.core.data

import com.nimdokai.midnite.core.data.api.MatchesApi
import com.nimdokai.midnite.core.data.model.MatchDetails
import com.nimdokai.midnite.core.data.model.MatchDetailsJson
import com.nimdokai.midnite.core.data.model.Matches
import com.nimdokai.midnite.core.data.model.MatchesJson
import java.io.IOException
import javax.inject.Inject

interface MatchesRepository {

    suspend fun getUpcomingMatches(): GetUpcomingMatchesResponse
    suspend fun getMatchDetails(matchID: Int): GetMatchDetailsResponse
}

sealed interface GetUpcomingMatchesResponse {
    data class Success(val matches: Matches) : GetUpcomingMatchesResponse
    object NoInternet : GetUpcomingMatchesResponse
    object ServerError : GetUpcomingMatchesResponse
}

sealed interface GetMatchDetailsResponse {
    data class Success(val matchDetails: MatchDetails) : GetMatchDetailsResponse
    object NoInternet : GetMatchDetailsResponse
    object ServerError : GetMatchDetailsResponse
}

class DefaultMatchesRepository @Inject constructor(
    private val matchesApi: MatchesApi
) : MatchesRepository {

    override suspend fun getUpcomingMatches(): GetUpcomingMatchesResponse {
        return try {
            val response = matchesApi.getMatches()
            if (response.isSuccessful) {
                val matchesJson = response.body()!!
                GetUpcomingMatchesResponse.Success(matchesJson.mapToResponse())
            } else {
                GetUpcomingMatchesResponse.ServerError
            }
        } catch (e: IOException) {
            GetUpcomingMatchesResponse.NoInternet
        } catch (e: Exception) {
            //ToDo Logging exception
            GetUpcomingMatchesResponse.ServerError
        }
    }

    override suspend fun getMatchDetails(matchID: Int): GetMatchDetailsResponse {
        return try {
            val response = matchesApi.getMatchDetails(matchID)
            if (response.isSuccessful) {
                val matchDetailsJson = response.body()!!
                GetMatchDetailsResponse.Success(matchDetailsJson.mapToResponse())
            } else {
                GetMatchDetailsResponse.ServerError
            }
        } catch (e: IOException) {
            GetMatchDetailsResponse.NoInternet
        } catch (e: Exception) {
            //ToDo Logging exception
            GetMatchDetailsResponse.ServerError
        }
    }
}

private fun MatchDetailsJson.mapToResponse(): MatchDetails {
    return MatchDetails(
        id = id,
        name = name,
        startTime = startTime,
        homeTeam = MatchDetails.Team(homeTeam, homeImageUrl),
        awayTeam = MatchDetails.Team(awayTeam, awayImageUrl),
        markets = markets.map { marketJson ->
            MatchDetails.Market(
                id = marketJson.id,
                name = marketJson.name,
                contracts = marketJson.contracts.mapToResponse()
            )
        }
    )
}

private fun List<MatchDetailsJson.MarketJson.ContractJson>.mapToResponse(): List<MatchDetails.Contract> {
    return map {
        MatchDetails.Contract(
            id = it.id,
            name = it.name,
            price = it.price
        )
    }
}

private fun MatchesJson.mapToResponse(): Matches {
    return Matches(
        data.map {
            Matches.Match(
                id = it.id,
                name = it.name,
                homeTeam = Matches.Team(it.homeTeam, it.homeImageUrl),
                awayTeam = Matches.Team(it.awayTeam, it.awayImageUrl),
                startTime = it.startTime,
            )
        }
    )
}
