package com.example.kotlinpracticemirea.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.databinding.BasicEquipmentListItemBinding

class EquipmentRecyclerViewAdapter(private val textItemsList: List<String>, private val iconsList:List<Int>)
    : RecyclerView.Adapter<EquipmentRecyclerViewAdapter.EquipmentViewHolder>() {

    class EquipmentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = BasicEquipmentListItemBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.basic_equipment_list_item ,parent,false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder , position: Int) {
        holder.binding.icon.setImageResource(iconsList[position])
        holder.binding.itemName.text = textItemsList[position]
    }

    override fun getItemCount(): Int {
        return iconsList.size
    }

}