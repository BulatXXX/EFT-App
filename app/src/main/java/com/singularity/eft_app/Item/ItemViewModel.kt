package com.singularity.eft_app.Item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository,
    ) :
    ViewModel() {

    val foundItems = repository.foundItems

    var searchJob: Job? = null

    val favouriteItems = repository.favouriteItems.asLiveData()

    val searchHistoryList = repository.searchHistoryList

    val searchFragmentState = MutableLiveData(SearchFragmentState.IS_IDLE)

    val isResponseSuccessful = repository.isResponseSuccessful

    fun searchItem(name: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.plus(Dispatchers.IO).launch {
            repository.getItemList(name)
        }
    }

    fun clearSearchBar() {
        searchJob?.cancel()
        searchJob = null
        foundItems.value = emptyList()
    }

    fun addToFavourites(item: Item) {
        viewModelScope.plus(Dispatchers.IO).launch {
            repository.addItemToFavourites(item)
        }
    }

    fun deleteFromFavourites(item: Item) {
        viewModelScope.plus(Dispatchers.IO).launch {
            repository.deleteItemFromFavourites(item)
        }
    }

    fun check(id: String, result: (Boolean) -> Unit) {
        viewModelScope.plus(Dispatchers.IO).launch() {
            val res = repository.checkIsFavourite(id)
            result.invoke(res)
        }
    }

    fun clearSearchHistory() {
        searchHistoryList.value = emptyList()
        repository.clearSearchHistory()
    }

    fun saveToSharedPreferences(item: Item) {
        repository.saveToSharedPreferences(item)
    }

    fun getHistoryList() {
        repository.getHistoryList()
    }
}

enum class SearchFragmentState {
    IS_SHOWING_HISTORY,
    IS_SHOWING_SEARCH_RESULT,
    IS_SEARCHING,
    IS_IDLE
}