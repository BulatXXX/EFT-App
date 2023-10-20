package com.example.kotlinpracticemirea.room

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.kotlinpracticemirea.Item.Item


@Database(entities = [Item::class], version = 1)
abstract class FleaMarketDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

}