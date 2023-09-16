package com.example.kotlinpracticemirea

import kotlinx.coroutines.flow.Flow

class FleaMarketItemRepository(private val fleaMarketDao: FleaMarketDao) {
    val allItems = fleaMarketDao.getAllItems()
    suspend fun addItem(fleaMarketItem: FleaMarketItem){
        fleaMarketDao.addItem(fleaMarketItem)
    }
}