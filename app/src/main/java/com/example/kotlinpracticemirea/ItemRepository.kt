package com.example.kotlinpracticemirea

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlinpracticemirea.room.FleaMarketItem
import com.example.kotlinpracticemirea.retrofit.ItemApi
import com.example.kotlinpracticemirea.retrofit.ItemInstance
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ItemRepository() {

    private val listOfFoundItems = MutableLiveData<List<Item>>(emptyList())

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

    fun addItemToFavourites(item: Item) {

    }

    fun getFavouritesList(): List<Item> {
        return emptyList()
    }

    fun deleteItemFromFavourites(item: Item) {

    }
}
