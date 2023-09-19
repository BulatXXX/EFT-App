package com.example.kotlinpracticemirea

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpracticemirea.databinding.FleaMarketItemBinding

class FleaMarketItemAdapter() :
    ListAdapter<FleaMarketItem , FleaMarketItemAdapter.FleaMarketItemHolder>(DiffCallback()) {
    class FleaMarketItemHolder(private val binding: FleaMarketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fleaMarketItem: FleaMarketItem) {
            binding.nameItem.text = fleaMarketItem.item_name
            binding.iconItem.setImageResource(fleaMarketItem.img)
            binding.priceItem.text = fleaMarketItem.item_price
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): FleaMarketItemHolder {
        val binding =
            FleaMarketItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FleaMarketItemHolder(binding)
    }


    override fun onBindViewHolder(holder: FleaMarketItemHolder , position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<FleaMarketItem>() {
        override fun areItemsTheSame(oldItem: FleaMarketItem , newItem: FleaMarketItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FleaMarketItem ,
            newItem: FleaMarketItem
        ): Boolean = oldItem == newItem
    }
}