package com.nimdokai.midnite.core.data.model

data class Matches(
    val data: List<Match>,
    //ToDo add data for pagination?
) {
    data class Match(
        val id: Int,
        val name: String,
        val homeTeam: Team,
        val awayTeam: Team,
        val startTime: String,
    )

    data class Team(
        val name: String,
        val imageUrl: String
    )
}
