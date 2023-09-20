package com.example.kotlinpracticemirea

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinpracticemirea.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [FleaMarketItem::class], version = 1)
abstract class FleaMarketDatabase: RoomDatabase() {
    abstract fun fleaMarketDao(): FleaMarketDao

    class Callback @Inject constructor(
        private val database: Provider<FleaMarketDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().fleaMarketDao()
            applicationScope.launch {
                dao.addItem(FleaMarketItem(null,"Red rebel ice pick","2,5KK",R.drawable.knife_icon_w))
                dao.addItem(FleaMarketItem(null,"Red Keycard","50KK",R.drawable.icon_plus_sign_w))
            }

        }
    }
}