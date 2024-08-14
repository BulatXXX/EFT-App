package com.singularity.eft_app.Item

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.singularity.eft_app.SearchHistoryManager
import com.singularity.eft_app.retrofit.ItemInstance

import com.singularity.eft_app.room.ItemDao
import com.google.gson.Gson
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val searchHistoryManager: SearchHistoryManager
) {
    var favouriteItems = itemDao.getAllItems()

    private val listOfFoundItems = MutableLiveData<List<Item>>(emptyList())
    val foundItems: MutableLiveData<List<Item>>
        get() = listOfFoundItems

    val isResponseSuccessful: MutableLiveData<Boolean> = MutableLiveData(true)

    val searchHistoryList = MutableLiveData<List<Item>>(emptyList())

    suspend fun getItemsListFromApi(
        name: String,
        language: String = "en",
        gameMode: String = "regular"
    ) {

        val query = """
        {
            items(lang: $language, name: "$name", gameMode: $gameMode) {
                id
                name
                description
                avg24hPrice
                height
                width
                iconLink
                image512pxLink
            }
        }
    """.trimIndent()
        try {
            val response = ItemInstance.ItemService.getItems(query)
            isResponseSuccessful.postValue(response.isSuccessful)
            val responseBody = response.body().toString()
            val gson = Gson()
            val responseData = gson.fromJson(responseBody, ResponseData::class.java)
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

    fun checkIsFavourite(id: String): Boolean = itemDao.getItemById(id) != null

    fun clearSearchHistory(context: Context) {
        searchHistoryManager.clearSearchHistory(context)
        searchHistoryList.postValue(emptyList())
    }

    fun saveToSharedPreferences(item: Item, context: Context) {
        searchHistoryManager.saveToSharedPreferences(item, context)
    }

    fun getHistoryList(context: Context) {
        searchHistoryList.postValue(searchHistoryManager.getHistoryList(context))
    }
}
