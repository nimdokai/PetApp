package com.nimdokai.pet.feature.categories.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.nimdokai.pet.feature.categories.databinding.ItemCategoryFeedBinding

internal class PetCategoryFeedAdapter(
    private val onFeedItemClicked: ViewHolder.OnFeedItemClicked
) : ListAdapter<PetCategoryFeedItemUI, PetCategoryFeedAdapter.ViewHolder>(CategoryItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onFeedItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemCategoryFeedBinding,
        private val onFeedItemClicked: OnFeedItemClicked
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PetCategoryFeedItemUI) = binding.run {
            feedImage.load(item.imageUrl)
            root.setOnClickListener { onFeedItemClicked.onClicked(item) }

            val lp = feedImage.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1f
            }
        }

        fun interface OnFeedItemClicked {
            fun onClicked(item: PetCategoryFeedItemUI)
        }
    }

    private object CategoryItemDiffUtil : DiffUtil.ItemCallback<PetCategoryFeedItemUI>() {

        override fun areItemsTheSame(
            oldItem: PetCategoryFeedItemUI,
            newItem: PetCategoryFeedItemUI,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PetCategoryFeedItemUI,
            newItem: PetCategoryFeedItemUI,
        ): Boolean {
            return oldItem == newItem
        }
    }

}