package com.example.kotlinpracticemirea

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FleaMarketItem::class], version = 1)
abstract class RoomDB: RoomDatabase() {
    abstract fun fleaMarketDao(): FleaMarketDao
}