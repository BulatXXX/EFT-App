package com.example.kotlinpracticemirea.Item

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlinpracticemirea.SearchHistoryManager
import com.example.kotlinpracticemirea.retrofit.ItemInstance

import com.example.kotlinpracticemirea.room.ItemDao
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemDao: ItemDao,private val searchHistoryManager: SearchHistoryManager) {
    var favouriteItems = itemDao.getAllItems()

    private val listOfFoundItems = MutableLiveData<List<Item>>(emptyList())
    val foundItems: MutableLiveData<List<Item>>
        get() = listOfFoundItems

    val isResponseSuccessful: MutableLiveData<Boolean> = MutableLiveData(true)

    val searchHistoryList = MutableLiveData<List<Item>>(emptyList())


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

            isResponseSuccessful.postValue(response.isSuccessful)

            var responseBody = response.body().toString()

            Log.e("response" , responseBody)
            val gson = Gson()
            val responseData = gson.fromJson(responseBody , ResponseData::class.java)
            val items = responseData.data.items
            foundItems.postValue(items)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            isResponseSuccessful.postValue(false)
        }
    }

    suspend fun addItemToFavourites(item: Item) {
        itemDao.addItem(item)
    }

    suspend fun deleteItemFromFavourites(item: Item) {
        itemDao.deleteItem(item)
    }

    fun checkIsFavourite(id: String):Boolean = itemDao.getItemById(id)!=null

    fun clearSearchHistory(context: Context){
        searchHistoryManager.clearSearchHistory(context)
        searchHistoryList.postValue(emptyList())
    }
    fun saveToSharedPreferences(item: Item , context: Context) {
        searchHistoryManager.saveToSharedPreferences(item,context)
    }
    fun getHistoryList(context: Context){
        searchHistoryList.postValue(searchHistoryManager.getHistoryList(context))
    }
}
