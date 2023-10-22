package com.example.kotlinpracticemirea.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpracticemirea.Item.Item
import com.example.kotlinpracticemirea.databinding.FleaMarketItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FleaMarketItemAdapter(private val listener: Listener) :
    ListAdapter<Item , FleaMarketItemAdapter.FleaMarketItemHolder>(DiffCallback()) {
    class FleaMarketItemHolder(private val binding: FleaMarketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        fun bind(item: Item , listener: Listener) {
            binding.nameItem.text = item.name
            // binding.iconItem.setImageResource(item.image512pxLink)
            CoroutineScope(Dispatchers.IO).launch {
                val iconUrl = item.iconLink
                try {
                    val inputStream = java.net.URL(iconUrl).openStream()
                    image = BitmapFactory.decodeStream(inputStream)

                    handler.post {
                        binding.iconItem.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            binding.nameItem.isSelected = true
            if (item.avg24hPrice == 0) {
                binding.priceItem.isSelected = true
                binding.priceItem.text = "Item can't be bought at a flea market!"
                binding.priceItem.setTextColor(android.graphics.Color.RED)
            } else {
                binding.priceItem.text = item.avg24hPrice.toString() + " RUB"
            }
            binding.item.setOnClickListener {
                listener.OnClick(item)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): FleaMarketItemHolder {
        val binding =
            FleaMarketItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FleaMarketItemHolder(binding)
    }


    override fun onBindViewHolder(holder: FleaMarketItemHolder , position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem , listener)
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
        fun OnClick(item: Item)
    }
}