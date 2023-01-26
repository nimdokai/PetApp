//package com.nimdokai.feature_petdetails.ui
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//
//private const val VIEW_TYPE_MARKET = 1
//private const val VIEW_TYPE_CONTRACT = 2
//
//internal class PetDetailsAdapter(
//) : ListAdapter<PetDetailsListItem, RecyclerView.ViewHolder>(PetItemDiffUtil) {
//
//    override fun getItemViewType(position: Int): Int {
//        TODO()
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): RecyclerView.ViewHolder {
//        TODO()
//        val layoutInflater = LayoutInflater.from(parent.context)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = getItem(position)
//        when (holder) {
//            is MarketViewHolder -> holder.bind(item as PetDetailsListItem)
//            is ContractViewHolder -> holder.bind(item as PetDetailsListItem)
//        }
//    }
//
//    class MarketViewHolder(
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: PetDetailsListItem) {
//            binding.apply {
//                TODO()
//            }
//        }
//    }
//
//    class ContractViewHolder(
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: PetDetailsListItem) {
//            binding.apply {
//                TODO()
//            }
//        }
//    }
//
//    private object PetItemDiffUtil : DiffUtil.ItemCallback<PetDetailsListItem>() {
//
//        override fun areItemsTheSame(
//            oldItem: PetDetailsListItem,
//            newItem: PetDetailsListItem,
//        ): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(
//            oldItem: PetDetailsListItem,
//            newItem: PetDetailsListItem,
//        ): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//
//}
//
//data class PetDetailsListItem(val id: Int)
