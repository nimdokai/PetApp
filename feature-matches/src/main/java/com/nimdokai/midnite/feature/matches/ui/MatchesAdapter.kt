package com.nimdokai.midnite.feature.matches.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nimdokai.midnite.core.resources.views.loadTeamImage
import com.nimdokai.midnite.feature.matches.databinding.ItemMatchBinding

internal class MatchesAdapter(
    private val matchClickedListener: ViewHolder.OnMatchClickedListener
) : ListAdapter<MatchItemUI, MatchesAdapter.ViewHolder>(MatchItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, matchClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemMatchBinding,
        private val onMatchClickedListener: OnMatchClickedListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchItemUI) {
            binding.apply {
                startTime.text = item.startTime
                homeTeam.text = item.homeTeam.name
                homeTeamImage.loadTeamImage(item.homeTeam.imageUrl)
                awayTeam.text = item.awayTeam.name
                awayTeamImage.loadTeamImage(item.awayTeam.imageUrl)
                root.setOnClickListener { onMatchClickedListener.onClicked(item) }
            }
        }


        fun interface OnMatchClickedListener {
            fun onClicked(MatchItem: MatchItemUI)
        }
    }

    private object MatchItemDiffUtil : DiffUtil.ItemCallback<MatchItemUI>() {

        override fun areItemsTheSame(
            oldItem: MatchItemUI,
            newItem: MatchItemUI,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MatchItemUI,
            newItem: MatchItemUI,
        ): Boolean {
            return oldItem == newItem
        }
    }

}