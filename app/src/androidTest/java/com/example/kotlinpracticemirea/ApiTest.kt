package com.example.kotlinpracticemirea

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.kotlinpracticemirea.room.FleaMarketDatabase
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ApiTest {



    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.databaseBuilder(
        context.applicationContext,
        FleaMarketDatabase::class.java,
        "items-database"
    )
        .fallbackToDestructiveMigration()
        .build()
    val itemDao = db.itemDao()





}

