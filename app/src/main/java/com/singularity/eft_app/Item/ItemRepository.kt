package com.singularity.eft_app.Item

import androidx.lifecycle.MutableLiveData
import com.singularity.eft_app.SearchHistoryManager

import com.singularity.eft_app.room.ItemDao
import com.singularity.apollo.type.GameMode
import com.singularity.apollo.type.LanguageCode
import com.singularity.eft_app.apollo.ItemClient
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val searchHistoryManager: SearchHistoryManager,
    private val apolloItemClient: ItemClient
) {
    var favouriteItems = MutableLiveData<List<Item>>()

    private val favouriteItemsIds = itemDao.getAllItemsIds()

    private val listOfFoundItems = MutableLiveData<List<Item>>(emptyList())
    val foundItems: MutableLiveData<List<Item>>
        get() = listOfFoundItems

    val isResponseSuccessful: MutableLiveData<Boolean> = MutableLiveData(true)

    val searchHistoryList = MutableLiveData<List<Item>>(emptyList())


    suspend fun getFavouritesItemsList(){
        favouriteItemsIds.collect() {
            favouriteItems.postValue(apolloItemClient.getItemByIds(it))
        }
    }

    suspend fun getItemList(
        name: String,
        languageCode: LanguageCode = LanguageCode.en,
        gameMode: GameMode = GameMode.regular
    ) {
        foundItems.postValue(apolloItemClient.searchItemsByName(name, languageCode, gameMode).orEmpty())
    }

    suspend fun addItemToFavourites(item: Item) {
        itemDao.addItem(item)
    }

    suspend fun deleteItemFromFavourites(item: Item) {
        itemDao.deleteItem(item)
    }

    fun checkIsFavourite(id: String): Boolean = itemDao.getItemById(id) != null

    fun clearSearchHistory() {
        searchHistoryManager.clearSearchHistory()
        searchHistoryList.postValue(emptyList())
    }

    fun saveToSharedPreferences(item: Item) {
        searchHistoryManager.saveToSharedPreferences(item)
    }

    fun getHistoryList() {
        searchHistoryList.postValue(searchHistoryManager.getHistoryList())
    }
}
