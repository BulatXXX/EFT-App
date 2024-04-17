package com.example.kotlinpracticemirea

import android.content.Context
import android.content.SharedPreferences
import com.example.kotlinpracticemirea.Item.Item

class SearchHistoryManager() {
    private val SHARED_PREFERENCES_NAME = "SEARCH_HISTORY"
    private val SEARCH_HISTORY_KEY = "HISTORY_LIST"

    fun clearSearchHistory(context: Context){
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME , Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(SEARCH_HISTORY_KEY,"").apply()
    }
    fun saveToSharedPreferences(item: Item , context: Context) {
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
    fun getHistoryList(context: Context): List<Item> {
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
/*
        val regex = "\\[(.*?), (.*?)\\]".toRegex()


        val matches = regex.findAll(searchHistoryListString)

        val items = matches.map {
            val name = it.groupValues[1]
            val iconLink = it.groupValues[2]
            Item(name, iconLink=iconLink)
        }.toList()*/
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