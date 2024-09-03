package com.singularity.eft_app.room

import androidx.room.Database
import androidx.room.RoomDatabase

import com.singularity.eft_app.Item.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class EFTAppDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

}