package com.example.kotlinpracticemirea

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class FleaMarketItemRepository(private val fleaMarketDao: FleaMarketDao) {
    val allItems = fleaMarketDao.getAllItems()

    suspend fun addItem(fleaMarketItem: FleaMarketItem){
        fleaMarketDao.addItem(fleaMarketItem)
    }
}