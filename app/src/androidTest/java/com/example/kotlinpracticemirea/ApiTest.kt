package com.example.kotlinpracticemirea

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.kotlinpracticemirea.Item.ItemRepository
import com.example.kotlinpracticemirea.room.FleaMarketDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
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

