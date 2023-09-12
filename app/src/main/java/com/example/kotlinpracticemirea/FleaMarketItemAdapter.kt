package com.example.kotlinpracticemirea

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpracticemirea.databinding.FleaMarketItemBinding

class FleaMarketItemAdapter(private val items:List<FleaMarketItem>): RecyclerView.Adapter<FleaMarketItemAdapter.FleaMarketItemHolder>() {
   class FleaMarketItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      var binding: FleaMarketItemBinding = FleaMarketItemBinding.bind(itemView)

   }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): FleaMarketItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flea_market_item, parent, false)
        return FleaMarketItemHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FleaMarketItemHolder , position: Int) {
        holder.binding.iconItem.setImageResource(items[position].img)
        holder.binding.nameItem.text = items[position].item_name
        holder.binding.priceItem.text = items[position].item_price
    }
}