package com.singularity.eft_app

import android.content.Context
import android.content.SharedPreferences

import com.singularity.eft_app.Item.Item
import javax.inject.Inject

class SearchHistoryManager @Inject constructor(
    private val context: Context
) {
    private val SHARED_PREFERENCES_NAME = "SEARCH_HISTORY"
    private val SEARCH_HISTORY_KEY = "HISTORY_LIST"

    fun clearSearchHistory(){
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME , Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(SEARCH_HISTORY_KEY,"").apply()
    }
    fun saveToSharedPreferences(item: Item) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME , Context.MODE_PRIVATE)
        val searchHistoryList = ArrayList<Item>(getHistoryList(sharedPreferences))

        if (!searchHistoryList.contains(item)){
            searchHistoryList.add(0 , item)
            //selected item must be on top even if is already on the list ()
        }

        if (searchHistoryList.size > 10) searchHistoryList.removeLast()

        val searchHistoryListString: String = parseItemsListToString(searchHistoryList)
        sharedPreferences.edit().putString(SEARCH_HISTORY_KEY,searchHistoryListString).apply()
    }
    fun getHistoryList(): List<Item> {
        val searchHistoryListString =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE).getString(SEARCH_HISTORY_KEY , "").toString()

        return parseItemsStringToList(searchHistoryListString)
    }
    private fun getHistoryList(sharedPreferences: SharedPreferences): List<Item> {
        val searchHistoryListString =
            sharedPreferences.getString(SEARCH_HISTORY_KEY , "").toString()

        return parseItemsStringToList(searchHistoryListString)
    }
    private fun parseItemsStringToList(searchHistoryListString: String): List<Item> {
        val searchHistoryList = ArrayList<Item>()
        val searchHistoryListStringArr = searchHistoryListString.split(" ; ")
        for (i in searchHistoryListStringArr){
            if (i.isNotEmpty()) searchHistoryList.add(Item.fromString(i))
        }
        return searchHistoryList

    }
    private fun parseItemsListToString(searchHistoryList: ArrayList<Item>): String {
        var searchHistoryListString = ""
        for (i in searchHistoryList) {
            searchHistoryListString += "$i ; "
        }
        return searchHistoryListString
    }
}