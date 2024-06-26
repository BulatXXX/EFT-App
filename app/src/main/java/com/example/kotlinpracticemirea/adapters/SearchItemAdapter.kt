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
import com.example.kotlinpracticemirea.R

import com.example.kotlinpracticemirea.databinding.SearchItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchItemAdapter(private val listener: Listener) :
    ListAdapter<Item , SearchItemAdapter.SearchItemHolder>(DiffCallback()) {
    class SearchItemHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item , listener: Listener) {
            binding.icon.setImageResource(R.drawable.knife_icon_w)

            val handler = Handler(Looper.getMainLooper())
            var image:Bitmap? = null
            CoroutineScope(Dispatchers.IO).launch{
                val iconUrl = item.iconLink
                try {
                    val inputStream = java.net.URL(iconUrl).openStream()
                    image = BitmapFactory.decodeStream(inputStream)
                    handler.post {
                        binding.icon.setImageBitmap(image)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            binding.name.text = item.name
            binding.name.isSelected = true
            binding.parentConstraint.setOnClickListener {
                listener.OnClick(item)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): SearchItemHolder {
        val binding =
            SearchItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return SearchItemHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchItemHolder , position: Int) {
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
        fun OnClick(item: Item)
    }
}