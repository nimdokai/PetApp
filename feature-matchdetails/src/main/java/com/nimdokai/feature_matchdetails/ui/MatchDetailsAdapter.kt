package com.nimdokai.feature_matchdetails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nimdokai.feature_matchdetails.ui.MatchDetailsUI.MatchDetailsListItem
import com.nimdokai.midnite.feature.matchdetails.databinding.ItemContractBinding
import com.nimdokai.midnite.feature.matchdetails.databinding.ItemMarketBinding

private const val VIEW_TYPE_MARKET = 1
private const val VIEW_TYPE_CONTRACT = 2

internal class MatchDetailsAdapter(
) : ListAdapter<MatchDetailsListItem, RecyclerView.ViewHolder>(MatchItemDiffUtil) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MatchDetailsListItem.Market -> VIEW_TYPE_MARKET
            is MatchDetailsListItem.Contract -> VIEW_TYPE_CONTRACT
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MARKET -> {
                val binding = ItemMarketBinding.inflate(layoutInflater, parent, false)
                MarketViewHolder(binding)
            }
            VIEW_TYPE_CONTRACT -> {
                val binding = ItemContractBinding.inflate(layoutInflater, parent, false)
                ContractViewHolder(binding)
            }
            else -> throw IllegalStateException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MarketViewHolder -> holder.bind(item as MatchDetailsListItem.Market)
            is ContractViewHolder -> holder.bind(item as MatchDetailsListItem.Contract)
        }
    }

    class MarketViewHolder(
        private val binding: ItemMarketBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchDetailsListItem.Market) {
            binding.apply {
                name.text = item.name
            }
        }
    }

    class ContractViewHolder(
        private val binding: ItemContractBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchDetailsListItem.Contract) {
            binding.apply {
                name.text = item.name
                price.text = item.price
            }
        }
    }

    private object MatchItemDiffUtil : DiffUtil.ItemCallback<MatchDetailsListItem>() {

        override fun areItemsTheSame(
            oldItem: MatchDetailsListItem,
            newItem: MatchDetailsListItem,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MatchDetailsListItem,
            newItem: MatchDetailsListItem,
        ): Boolean {
            return oldItem == newItem
        }
    }


}