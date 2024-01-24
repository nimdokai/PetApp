package com.nimdokai.pet.feature.categories.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nimdokai.pet.feature.categories.databinding.ItemCategoryBinding

internal class CategoriesAdapter(
    private val onCategoryClickedListener: ViewHolder.OnCategoryClickedListener
) : ListAdapter<CurrentWeatherUi, CategoriesAdapter.ViewHolder>(CategoryItemDiffUtil) {

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
        fun bind(item: CurrentWeatherUi) {
            binding.apply {
//                categoryTitle.text = item.name
//                categoryImage.load(item.imageUrl)
//                root.setOnClickListener { onCategoryClickedListener.onClicked(item) }
            }
        }

        fun interface OnCategoryClickedListener {
            fun onClicked(category: CurrentWeatherUi)
        }
    }

    private object CategoryItemDiffUtil : DiffUtil.ItemCallback<CurrentWeatherUi>() {

        override fun areItemsTheSame(
            oldItem: CurrentWeatherUi,
            newItem: CurrentWeatherUi,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CurrentWeatherUi,
            newItem: CurrentWeatherUi,
        ): Boolean {
            return oldItem == newItem
        }
    }

}