package com.nimdokai.pet.feature.categories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nimdokai.pet.feature.categories.databinding.ItemCategoryBinding

internal class CategoriesAdapter(
    private val onCategoryClickedListener: ViewHolder.OnCategoryClickedListener
) : ListAdapter<PetCategoryItemUI, CategoriesAdapter.ViewHolder>(CategoryItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onCategoryClickedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemCategoryBinding,
        private val onCategoryClickedListener: OnCategoryClickedListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PetCategoryItemUI) {
            binding.apply {
                categoryTitle.text = item.name
                root.setOnClickListener { onCategoryClickedListener.onClicked(item) }
            }
        }

        fun interface OnCategoryClickedListener {
            fun onClicked(MatchItem: PetCategoryItemUI)
        }
    }

    private object CategoryItemDiffUtil : DiffUtil.ItemCallback<PetCategoryItemUI>() {

        override fun areItemsTheSame(
            oldItem: PetCategoryItemUI,
            newItem: PetCategoryItemUI,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PetCategoryItemUI,
            newItem: PetCategoryItemUI,
        ): Boolean {
            return oldItem == newItem
        }
    }

}