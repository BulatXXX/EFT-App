package com.example.kotlinpracticemirea.room


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FleaMarketDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<FleaMarketItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(fleaMarketItem: FleaMarketItem)

    @Delete
    suspend fun deleteItem(fleaMarketItem: FleaMarketItem)
}