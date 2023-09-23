package com.example.kotlinpracticemirea.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlinpracticemirea.R

@Entity(tableName = "items")
data class FleaMarketItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null ,
    val item_name: String ,
    val item_price: String ,
    val img: Int = R.drawable.img_1 ,
)
