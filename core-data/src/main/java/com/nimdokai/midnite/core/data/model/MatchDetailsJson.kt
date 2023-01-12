package com.nimdokai.midnite.core.data.model

import com.google.gson.annotations.SerializedName

data class MatchDetailsJson(
    @SerializedName("away_image_url")
    val awayImageUrl: String,
    @SerializedName("away_team")
    val awayTeam: String,
    @SerializedName("away_team_id")
    val awayTeamId: Int,
    @SerializedName("best_of")
    val bestOf: Int,
    @SerializedName("competition_id")
    val competitionId: Int,
    @SerializedName("competition_name")
    val competitionName: String,
    @SerializedName("competition_slug")
    val competitionSlug: String,
    @SerializedName("displayed_format")
    val displayedFormat: String,
    @SerializedName("game_id")
    val gameId: Int,
    @SerializedName("game_name")
    val gameName: String,
    @SerializedName("home_image_url")
    val homeImageUrl: String,
    @SerializedName("home_team")
    val homeTeam: String,
    @SerializedName("home_team_id")
    val homeTeamId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("live_enabled")
    val liveEnabled: Boolean,
    @SerializedName("main_market_id")
    val mainMarketId: Int,
    @SerializedName("market_filters")
    val marketFilters: List<MarketFilterJson>,
    @SerializedName("markets")
    val markets: List<MarketJson>,
    @SerializedName("multi_available")
    val multiAvailable: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("num_bets_placed")
    val numBetsPlaced: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("stream_url")
    val streamUrl: String,
    @SerializedName("streams")
    val streams: List<StreamJson>,
    @SerializedName("twitch_channel")
    val twitchChannel: String,
    @SerializedName("type")
    val type: String
) {
    data class MarketFilterJson(
        @SerializedName("market_ids")
        val marketIds: List<Int>,
        @SerializedName("name")
        val name: String
    )

    data class MarketJson(
        @SerializedName("contracts")
        val contracts: List<ContractJson>,
        @SerializedName("display_order")
        val displayOrder: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("match_id")
        val matchId: Int,
        @SerializedName("multi_available")
        val multiAvailable: Boolean,
        @SerializedName("name")
        val name: String,
        @SerializedName("popular")
        val popular: Boolean,
        @SerializedName("status")
        val status: String
    ) {
        data class ContractJson(
            @SerializedName("boosted")
            val boosted: Boolean,
            @SerializedName("display_order")
            val displayOrder: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("market_id")
            val marketId: Int,
            @SerializedName("max_bet")
            val maxBet: String,
            @SerializedName("multi_available")
            val multiAvailable: Boolean,
            @SerializedName("name")
            val name: String,
            @SerializedName("payout_type")
            val payoutType: String,
            @SerializedName("price")
            val price: String,
            @SerializedName("raw_price")
            val rawPrice: String,
            @SerializedName("raw_status")
            val rawStatus: String,
            @SerializedName("status")
            val status: String
        )
    }

    data class StreamJson(
        @SerializedName("channel")
        val channel: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("default")
        val default: Boolean,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: String
    )
}