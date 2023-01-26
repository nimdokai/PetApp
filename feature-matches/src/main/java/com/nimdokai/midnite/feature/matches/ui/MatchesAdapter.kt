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
) : ListAdapter<AnimalCategoryItemUI, MatchesAdapter.ViewHolder>(MatchItemDiffUtil) {

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
        fun bind(item: AnimalCategoryItemUI) {
            binding.apply {
                categoryTitle.text = item.name
                root.setOnClickListener { onMatchClickedListener.onClicked(item) }
            }
        }

        fun interface OnMatchClickedListener {
            fun onClicked(MatchItem: AnimalCategoryItemUI)
        }
    }

    private object MatchItemDiffUtil : DiffUtil.ItemCallback<AnimalCategoryItemUI>() {

        override fun areItemsTheSame(
            oldItem: AnimalCategoryItemUI,
            newItem: AnimalCategoryItemUI,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnimalCategoryItemUI,
            newItem: AnimalCategoryItemUI,
        ): Boolean {
            return oldItem == newItem
        }
    }

}