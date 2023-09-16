package com.example.kotlinpracticemirea

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FleaMarketDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<FleaMarketItem>>

    @Insert
    suspend fun addItem(fleaMarketItem: FleaMarketItem)
}