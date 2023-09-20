package com.example.kotlinpracticemirea

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class FleaMarketItemRepository @Inject constructor(private val fleaMarketDao: FleaMarketDao) {
    var allItems = fleaMarketDao.getAllItems()

    suspend fun addItem(fleaMarketItem: FleaMarketItem){
        println("BOOBS 3 $fleaMarketItem")
        fleaMarketDao.addItem(fleaMarketItem)
    }

}