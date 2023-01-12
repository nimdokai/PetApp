package com.nimdokai.midnite.feature.matches.ui

import com.nimdokai.midnite.core.data.model.Matches

data class MatchItemUI(
    val id: Int,
    val name: String,
    val homeTeam: TeamUI,
    val awayTeam: TeamUI,
    val startTime: String,
)

data class TeamUI(
    val name: String,
    val imageUrl: String
)

internal fun Matches.Team.toUI() = TeamUI(name, imageUrl)
