package com.example.kotlinpracticemirea

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.kotlinpracticemirea.room.FleaMarketItem
import com.example.kotlinpracticemirea.retrofit.ItemApi
import com.example.kotlinpracticemirea.retrofit.ItemInstance

import com.example.kotlinpracticemirea.room.ItemDao
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemDao: ItemDao) {
    var favouriteItems = itemDao.getAllItems()

    private val listOfFoundItems = MutableLiveData<List<Item>>(emptyList())
    var selectedItem = MutableLiveData<String>()

    val foundItems: MutableLiveData<List<Item>>
        get() = listOfFoundItems






    suspend fun getItemsListFromApi(name: String) {
        val paramObject = JSONObject()
        paramObject.put(
            "query" , "query {items(name:\"$name\"){id\n" +
                    "    name\n" +
                    "    description\n" +
                    "    avg24hPrice\n" +
                    "    height\n" +
                    "    width\n" +
                    "    iconLink\n" +
                    "    image512pxLink}}"
        )

        try {
            val response = ItemInstance.ItemService.getItems(paramObject.toString())
            var responseBody = response.body().toString()

            Log.e("response" , responseBody)
            val gson = Gson()
            val responseData = gson.fromJson(responseBody , ResponseData::class.java)
            val items = responseData.data.items
            foundItems.postValue(items)


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }


    }

    suspend fun addItemToFavourites(item: Item) {
        itemDao.addItem(item)
    }

    fun getFavouritesList(): List<Item> {
        return emptyList()
    }

    suspend fun deleteItemFromFavourites(item: Item) {
        itemDao.deleteItem(item)
    }

    fun checkIsFavourite(id: String):Boolean = itemDao.getItemById(id)!=null
}
