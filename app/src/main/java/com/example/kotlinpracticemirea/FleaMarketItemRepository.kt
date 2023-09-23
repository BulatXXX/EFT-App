package com.example.kotlinpracticemirea

import com.example.kotlinpracticemirea.room.FleaMarketDao
import com.example.kotlinpracticemirea.room.FleaMarketItem

import javax.inject.Inject

class FleaMarketItemRepository @Inject constructor(private val fleaMarketDao: FleaMarketDao) {
    var allItems = fleaMarketDao.getAllItems()

    suspend fun addItem(fleaMarketItem: FleaMarketItem){
        fleaMarketDao.addItem(fleaMarketItem)
    }

}