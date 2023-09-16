package com.example.kotlinpracticemirea

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FleaMarketItemViewModel(context: Context) : ViewModel() {
    private val fleaMarketItemRepository = FleaMarketItemRepository(getDb(context).fleaMarketDao())
    private fun getDb(context: Context): RoomDB {
        return Room.databaseBuilder(context , RoomDB::class.java , "flea-database").build()
    }

    val allItems = getDb(context).fleaMarketDao().getAllItems()

    fun addItem(fleaMarketItem: FleaMarketItem) {
        viewModelScope.launch {
            fleaMarketItemRepository.addItem(fleaMarketItem)
        }
    }
}