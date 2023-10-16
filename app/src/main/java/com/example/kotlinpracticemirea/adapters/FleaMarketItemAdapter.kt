package com.example.kotlinpracticemirea.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpracticemirea.Item
import com.example.kotlinpracticemirea.databinding.FleaMarketItemBinding
import com.example.kotlinpracticemirea.room.FleaMarketItem

class FleaMarketItemAdapter() :
    ListAdapter<Item , FleaMarketItemAdapter.FleaMarketItemHolder>(DiffCallback()) {
    class FleaMarketItemHolder(private val binding: FleaMarketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.nameItem.text = item.name
           // binding.iconItem.setImageResource(item.image512pxLink)
            binding.priceItem.text = item.avg24hPrice.toString()
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

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item , newItem: Item): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Item ,
            newItem: Item
        ): Boolean = oldItem == newItem
    }
}