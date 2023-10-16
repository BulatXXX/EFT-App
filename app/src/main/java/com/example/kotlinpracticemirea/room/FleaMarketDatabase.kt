package com.example.kotlinpracticemirea.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinpracticemirea.Item
import com.example.kotlinpracticemirea.R
import com.example.kotlinpracticemirea.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Item::class], version = 1)
abstract class FleaMarketDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    class Callback @Inject constructor(
        private val database: Provider<FleaMarketDatabase> ,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().itemDao()
            applicationScope.launch {
              //  dao.addItem(Item(""))

            }

        }
    }
}