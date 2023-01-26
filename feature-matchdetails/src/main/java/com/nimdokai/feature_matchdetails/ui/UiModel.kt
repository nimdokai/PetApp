package com.nimdokai.feature_matchdetails.ui

import com.nimdokai.core_util.navigation.date.DateFormatter
import com.nimdokai.feature_matchdetails.ui.MatchDetailsUI.MatchDetailsListItem
import com.nimdokai.midnite.core.data.model.AnimalDetails

data class MatchDetailsUI(
    val id: Int,
    val name: String,
    val startTime: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val detailsItems: List<MatchDetailsListItem>
) {
    data class Team(
        val name: String,
        val imageUrl: String
    )

    sealed interface MatchDetailsListItem {
        val id: Int

        data class Market(
            override val id: Int,
            val name: String,
        ) : MatchDetailsListItem

        data class Contract(
            override val id: Int,
            val name: String,
            val price: String,
        ) : MatchDetailsListItem
    }

}

internal fun AnimalDetails.mapToUI(dateFormatter: DateFormatter): MatchDetailsUI {
    return MatchDetailsUI(
        id,
        name,
        dateFormatter.formatOnlyHourIfToday(startTime),
        homeTeam.mapToUI(),
        awayTeam.mapToUI(),
        markets.flatMap { market: AnimalDetails.Market ->
            val map: List<MatchDetailsListItem> = market.contracts.map {
                it.mapToUI()
            }
            return@flatMap mutableListOf<MatchDetailsListItem>(market.mapToUI()).apply { addAll(map) }
        }
    )
}


private fun AnimalDetails.Team.mapToUI(): MatchDetailsUI.Team {
    return MatchDetailsUI.Team(name, imageUrl)
}

private fun AnimalDetails.Market.mapToUI(): MatchDetailsListItem.Market {
    return MatchDetailsListItem.Market(
        id,
        name,
    )
}

private fun AnimalDetails.Contract.mapToUI(): MatchDetailsListItem.Contract {
    return MatchDetailsListItem.Contract(id, name, price)
}

