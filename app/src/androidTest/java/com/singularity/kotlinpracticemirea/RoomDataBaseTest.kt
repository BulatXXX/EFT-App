package com.singularity.kotlinpracticemirea

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.eft_app.Item.Item
import com.example.eft_app.room.FleaMarketDatabase
import com.example.eft_app.room.ItemDao
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class RoomDataBaseTest {
    private lateinit var userDao: ItemDao
    private lateinit var db: FleaMarketDatabase

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FleaMarketDatabase::class.java
        ).build()
        userDao = db.itemDao()
    }

    @Test
    fun shouldInsertItem() = runBlocking {
        val insertedItem = Item(id = "2", name = "Item")

        userDao.addItem(insertedItem)

        Assert.assertEquals("Item", userDao.getItemById("2"))
    }

    @Test
    fun shouldDeleteItem() = runBlocking {
        userDao.deleteItem(Item(id = "2", name = "Item"))
        Assert.assertEquals(null, userDao.getItemById("2"))
    }

    @After
    fun closeDb() {
        db.close()
    }
}