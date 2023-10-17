package com.example.kotlinpracticemirea.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinpracticemirea.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item:Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT name FROM items WHERE id = :id")
    fun getItemById(id: String): String?
}