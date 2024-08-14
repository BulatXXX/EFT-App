package com.singularity.eft_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.singularity.eft_app.Item.Item
import com.singularity.eft_app.R
import com.singularity.eft_app.databinding.SearchItemBinding

class SearchItemAdapter(private val listener: Listener) :
    ListAdapter<Item , SearchItemAdapter.SearchItemHolder>(DiffCallback()) {
    class SearchItemHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item , listener: Listener) {
            binding.icon.setImageResource(R.drawable.knife_icon_w)
            Glide.with(binding.icon).load(item.iconLink).placeholder(R.drawable.knife_icon_w).into(binding.icon)
            binding.name.text = item.name
            binding.name.isSelected = true
            binding.parentConstraint.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): SearchItemHolder {
        val binding =
            SearchItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return SearchItemHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchItemHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item , newItem: Item): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Item ,
            newItem: Item
        ): Boolean = oldItem == newItem
    }
    interface Listener {
        fun onClick(item: Item)
    }
}