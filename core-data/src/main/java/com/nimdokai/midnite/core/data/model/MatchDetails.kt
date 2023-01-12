package com.nimdokai.midnite.core.data.model

data class MatchDetails(
    val id: Int,
    val name: String,
    val startTime: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val markets: List<Market>
) {
    data class Team(
        val name: String,
        val imageUrl: String
    )

    data class Market(
        val id: Int,
        val name: String,
        val contracts: List<Contract>,
    )
    data class Contract(
        val id: Int,
        val name: String,
        val price: String,
    )
}

