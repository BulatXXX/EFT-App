package com.example.kotlinpracticemirea

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class FleaMarketItem(
    val item_name: String ,
    val item_price: String ,
    val img: Int = R.drawable.img_1 ,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
