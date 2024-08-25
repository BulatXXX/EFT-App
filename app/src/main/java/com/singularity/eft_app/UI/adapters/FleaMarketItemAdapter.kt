package com.singularity.eft_app.UI.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singularity.eft_app.Item.Item
import com.singularity.eft_app.databinding.FleaMarketItemBinding

class FleaMarketItemAdapter(private val listener: Listener) :
    ListAdapter<Item, FleaMarketItemAdapter.FleaMarketItemHolder>(DiffCallback()) {
    class FleaMarketItemHolder(private val binding: FleaMarketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Item, listener: Listener) {
            binding.nameItem.text = item.name
            Glide.with(binding.iconItem).load(item.iconLink).into(binding.iconItem)
            binding.nameItem.isSelected = true
            if (item.avg24hPrice == 0) {
                binding.priceItem.isSelected = true
                binding.priceItem.text = "Item can't be bought at a flea market!"
                binding.priceItem.setTextColor(android.graphics.Color.RED)
            } else {
                binding.priceItem.text = item.avg24hPrice.toString() + " RUB"
            }
            binding.item.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): FleaMarketItemHolder {
        val binding =
            FleaMarketItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FleaMarketItemHolder(binding)
    }

    override fun onBindViewHolder(holder: FleaMarketItemHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem , listener)
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean = oldItem == newItem
    }

    interface Listener {
        fun onClick(item: Item)
    }
}