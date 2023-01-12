package com.nimdokai.midnite.core.data.api

import com.nimdokai.midnite.core.data.model.MatchDetailsJson
import com.nimdokai.midnite.core.data.model.MatchesJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface MatchesApi {

    @GET("/v0/matches/upcoming")
    suspend fun getMatches(): Response<MatchesJson>

    @GET("/v0/matches/{matchID}")
    suspend fun getMatchDetails(@Path("matchID") matchID: Int): Response<MatchDetailsJson>

}